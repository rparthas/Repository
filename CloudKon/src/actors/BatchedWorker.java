package actors;

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

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.util.ConcurrentHashSet;

import entity.QueueDetails;
import entity.Task;
import entity.TaskBatch;

public class BatchedWorker {
	private HazelcastClient hazelClinetObj;
	private int numberofWorkerThreads = 10;
	private ThreadPoolExecutor threadPoolExecutor;
	private Map<String, List<Task>> resultMap;
	private Map<Task, Future<Boolean>> taskMap;
	private List<Task> resultTask;
	private Properties properties;
	boolean clientNomoreTask = false;
	private String name;
	Semaphore objSemaphore = new Semaphore(1);
	public BatchedWorker() {
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
		BatchedWorker objWorker = new BatchedWorker();
		objWorker.setName(args[0]);
		// timer.schedule(objWorker, 2000, 2000);
		// timer.schedule(poller, 0, 2000);
		// Loop never ends once the worker begins execution
		WorkerMonitor.incrNumOfWorkerThreads(objWorker.hazelClinetObj);
		DistributedQueue queue = QueueFactory.getQueue();

		while (true) {
			// Get one Q information
			PrintManager.PrintMessage(objWorker.name+" Getting Queue Information for Client");
			QueueDetails queueDetails = queue.pullFromQueue();
			int cuncCurrentTask = 0;
			// loop for getting the tasks for the client mentioned in Q
			// information we got.
			// iteration per worker
			// objWorker.interation > 0
			while (true) {
				objWorker.clientNomoreTask = false;
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
					if(task.isMultiTask()){
						Set<Task> tasks = task.getTasks();
						for(Task eachTask:tasks){
							cuncCurrentTask = poolTask(objWorker,
									cuncCurrentTask, eachTask);
						}
					}else{
						cuncCurrentTask = poolTask(objWorker,
								cuncCurrentTask, task);
					}
				} else {
					objWorker.clientNomoreTask = true;
					PrintManager.PrintMessage("No more tasks with "
							+ queueDetails.getClientName());
					if(objWorker.taskMap.size()>0)
					objWorker.sendResults(objWorker);
					break;// breaks if no tasks in client queue
				}
				if (cuncCurrentTask >= objWorker.numberofWorkerThreads) {
					cuncCurrentTask = 0;
					objWorker.sendResults(objWorker);
				}
			}
		}
	}

	private static int poolTask(BatchedWorker objWorker, int cuncCurrentTask,
			Task task) {
		task.setWorker(objWorker.name);
		Future<Boolean> future = objWorker.threadPoolExecutor
				.submit(task);
		cuncCurrentTask++;
		objWorker.taskMap.put(task, future);
		return cuncCurrentTask;
	}

	public void sendResults(BatchedWorker objWorker) {
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
		if (resultTask != null
				&& ((resultTask.size() >= numberofWorkerThreads) || clientNomoreTask)) {
			Set<Task> tasks = new LinkedHashSet<Task>(resultTask);
			Task task = resultTask.get(0);
			Set<Task> batches = new ConcurrentHashSet<Task> ();
			Task taskBatch = new TaskBatch();
			taskBatch.setTasks(tasks);
			batches.add(taskBatch);
			TaskQueueFactory.getQueue().postTask(objSemaphore,batches,
					task.getResponseQueueName(), task.getQueueUrl(),
					task.getClientName());
		}
	}

	public boolean isBusy() {
		boolean isBusy = taskMap.isEmpty() && resultMap.isEmpty()
				&& threadPoolExecutor.getActiveCount() <= 0;
		return isBusy;
	}

}

