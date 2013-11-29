package actors;

import static utility.Constants.FINISHED;
import static utility.Constants.WORKER_STATUS;
import static utility.Constants.WORKER_COUNT_STATUS;
import static utility.Constants.BUSY;
import static utility.Constants.FREE;
import static utility.Constants.STARTED;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import monitor.WorkerMonitor;
import queue.DistributedQueue;
import queue.QueueFactory;
import queue.TaskQueueFactory;
import queue.hazelcast.QueueHazelcastUtil;
import utility.PrintManager;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.util.ConcurrentHashSet;

import entity.QueueDetails;
import entity.Task;
import entity.TaskBatch;

public class WorkerNew implements Runnable {
	private HazelcastClient hazelClinetObj;
	private int numberofWorkerThreads = 10;
	private ThreadPoolExecutor threadPoolExecutor;
	private Map<String, List<Task>> resultMap;
	private Map<Task, Future<Boolean>> taskMap;
	private List<Task> resultTask;
	private Properties properties;
	boolean clientNomoreTask = false;
	private String name;
	int workerWasteLimit = 0;
	int numWorkersPernode = 0;
	boolean workerSelftermEnabled = false;
	Semaphore objSemaphore = new Semaphore(1);
	boolean isblockedforClient = true;
	private long workerPolltime;
	public WorkerNew() {
		super();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			properties = new Properties();
			properties.load(reader);
			resultTask = new ArrayList<>();
			threadPoolExecutor = new ThreadPoolExecutor(numberofWorkerThreads,
					numberofWorkerThreads, 0L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
			// hazelClient
			this.hazelClinetObj = new QueueHazelcastUtil().getClient();
			this.numberofWorkerThreads = Integer.parseInt(properties
					.getProperty("numberofWorkerThreads"));
			this.resultMap = new ConcurrentHashMap<>();
			this.taskMap = new ConcurrentHashMap<>();
			workerWasteLimit = Integer.parseInt(properties
					.getProperty("workerWasteLimit"));
			numWorkersPernode = Integer.parseInt(properties
					.getProperty("numWorkers"));
			workerSelftermEnabled = properties.getProperty(
					"workerSelftermEnabled").equals("true");

			workerPolltime = Long.parseLong(properties
					.getProperty("workerPolltime"));
		} catch (FileNotFoundException e) {
			PrintManager.PrintException(e);
		} catch (IOException e) {
			PrintManager.PrintException(e);
		}

	}

	private void setName(String strName) {
		this.name = "worker " + strName;
	}

	/**
	 * Main method for starting the worker
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		WorkerNew objWorker = new WorkerNew();
		objWorker.setName(args[0]);
		Thread objTh = new Thread(objWorker);
		DistributedQueue queue = QueueFactory.getQueue();
		QueueDetails queueDetails = queue.pullFromQueue();
		WorkerMonitor.incrNumOfWorkerThreads(objWorker.hazelClinetObj);
		objWorker.recordWorkerCount();
		objTh.start();
		// Loop never ends once the worker begins execution

		while (true) {
			// Get one Q information
			PrintManager.PrintMessage(objWorker.name
					+ " Getting Queue Information for Client");
			objWorker.isblockedforClient = true;
			if(queueDetails==null){
				queueDetails = queue.pullFromQueue();
			}
			objWorker.isblockedforClient = false;
			int cuncCurrentTask = 0;
			// loop for getting the tasks for the client mentioned in Q
			// information we got.
			while (true) {
				// Pulling only tasks for the worker threads
				if (queueDetails == null) {
					break;
				}

				PrintManager.PrintMessage("Getting Task Information for Client"
						+ queueDetails.getClientName());
				Task task = TaskQueueFactory.getQueue().retrieveTask(
						queueDetails.getRequestQueue(), queueDetails.getUrl(),
						queueDetails.getClientName());
				if (task != null) {
					// Starting the Task
					task.setWorker(objWorker.name);
					Future<Boolean> future = objWorker.threadPoolExecutor
							.submit(task);
					cuncCurrentTask++;
					objWorker.taskMap.put(task, future);
				} else {
					objWorker.clientNomoreTask = true;
					PrintManager.PrintMessage("No more tasks with "
							+ queueDetails.getClientName());
					if (objWorker.taskMap.size() > 0)
						objWorker.sendResults(objWorker);
					break;// breaks if no tasks in client queue
				}
				if (cuncCurrentTask >= objWorker.numberofWorkerThreads) {
					cuncCurrentTask = 0;
					objWorker.sendResults(objWorker);
				}
			}
			queueDetails=null;
		}
	}

	public void sendResults(WorkerNew objWorker) {
		for (Task task : objWorker.taskMap.keySet()) {
			Future<Boolean> future = taskMap.get(task);
			try {
				Boolean result = future.get();
				if (result != null && true == result) {
					objWorker.taskMap.remove(task);
					objWorker.resultTask.add(task);
				}
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}
		}
		sendBatchResults(objWorker.resultTask);
		resultTask.clear();
	}

	private void sendBatchResults(List<Task> resultTask) {
		Set<Task> tasks = new LinkedHashSet<Task>(resultTask);
		if (tasks != null
				&& ((tasks.size() >= numberofWorkerThreads) || clientNomoreTask)) {
			Task task = tasks.iterator().next();
			Set<Task> batches = new ConcurrentHashSet<Task>();
			Task taskBatch = new TaskBatch();
			taskBatch.setTasks(tasks);
			batches.add(taskBatch);
			TaskQueueFactory.getQueue().postTask(objSemaphore, batches,
					task.getResponseQueueName(), task.getQueueUrl(),
					task.getClientName());
		}
	}

	public boolean isBusy() {
		boolean isfree = taskMap.isEmpty() && resultMap.isEmpty()
				&& threadPoolExecutor.getActiveCount() <= 0
				&& this.isblockedforClient;
		return !isfree;
	}

	@Override
	public void run() {
		int wastedseconds = 0;
		boolean breakflag = false;
		try {
			String whoami = "test";
			recordWorkerStatus(whoami + name + "," + STARTED);
			whoami = WorkerMonitor.retrieveInstanceId();
			Thread.sleep(3000);
			while (!breakflag) {
				boolean busyFalg = isBusy();
				PrintManager.PrintMessage("Worker status is :" + busyFalg);
				if (busyFalg) {
					wastedseconds = 0;
					WorkerMonitor.incrBusyetAtomicNumber(hazelClinetObj);
					recordWorkerStatus(whoami + name + "," + BUSY);
				} else {
					wastedseconds++;
					recordWorkerStatus(whoami + name + "," + FREE);
					recordWorkerCount();
					WorkerMonitor.incrFreeWorkerCount(hazelClinetObj);
					if (wastedseconds >= workerWasteLimit) {
						breakflag = true;
						if (workerSelftermEnabled) {
							// Terminate worker
							WorkerMonitor
									.decrNumOfWorkerThreads(this.hazelClinetObj);
							// make sure all workers on the ami are idle before
							// terminating
							Map<String, String> amiMap = hazelClinetObj
									.getMap(whoami);
							amiMap.put(name, FINISHED);
							while (hazelClinetObj.getMap(whoami).size() < numWorkersPernode) {
								PrintManager.PrintProdMessage(""
										+ hazelClinetObj.getMap(whoami).size());
								Thread.yield();
							}
							PrintManager.PrintProdMessage("Terminating worker" +whoami);
							recordWorkerStatus(whoami + name + "," + FINISHED);
							terminateMe(whoami);
						}

					}
				}
				Thread.sleep(workerPolltime);
			}
		} catch (InterruptedException e) {
			PrintManager.PrintException(e);
		}

	}

	private void recordWorkerCount() {
		ConcurrentMap<String, Long> mapWorkerCountStatus = hazelClinetObj
				.getMap(WORKER_COUNT_STATUS);
		long workCount = WorkerMonitor
				.getNumOfWorkerThreads(this.hazelClinetObj);
		String time = String.valueOf(System.nanoTime());
		mapWorkerCountStatus.putIfAbsent(time, workCount);
	}

	private void recordWorkerStatus(String ami_busy_free) {
		ConcurrentMap<String, String> mapWorkerStatus = hazelClinetObj
				.getMap(WORKER_STATUS);
		String time = String.valueOf(System.nanoTime());
		mapWorkerStatus.putIfAbsent(time, ami_busy_free);
	}

	private void terminateMe(String whoami) {
		List<String> instanceIdTerms = new ArrayList<>();
		AmazonEC2 ec2 = new AmazonEC2Client(
				new ClasspathPropertiesFileCredentialsProvider());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(usWest2);
		// adding the current instance id
		instanceIdTerms.add(whoami);
		TerminateInstancesRequest term = new TerminateInstancesRequest(
				instanceIdTerms);
		// close all cleints and other things before this.
		// termination request sent.
		term.setInstanceIds(instanceIdTerms);
		//StopInstancesRequest stop = new StopInstancesRequest(instanceIdTerms);
		//ec2.stopInstances(stop);
		hazelClinetObj.shutdown();
		ec2.terminateInstances(term);
		
	}

}
