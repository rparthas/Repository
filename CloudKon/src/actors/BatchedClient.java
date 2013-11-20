package actors;

import static utility.Constants.CLIENT_STATUS;
import static utility.Constants.FINISHED;
import static utility.Constants.IO_TASK;
import static utility.Constants.REQUESTQ;
import static utility.Constants.RESPONSEQ;
import static utility.Constants.SLEEP_TASK;
import static utility.Constants.STARTED;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

import monitor.ClientMonior;
import monitor.WorkerMonitor;
import monitor.cassandra.SimpleClient;
import queue.DistributedQueue;
import queue.QueueFactory;
import queue.TaskQueueFactory;
import queue.hazelcast.QueueHazelcastUtil;
import utility.PrintManager;

import com.datastax.driver.core.utils.UUIDs;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.util.ConcurrentHashSet;

import entity.QueueDetails;
import entity.Task;
import entity.TaskBatch;
import entity.TemplateIOTask;
import entity.TemplateTask;

public class BatchedClient implements Runnable {

	Map<String, Task> submittedTasks;
	String url;
	QueueDetails qu;
	long pollTime = 3000;
	HazelcastClient hazelClinetObj;
	long numberofWorkerThreads = 1;
	String fileName;
	String resouceAllocationMode;
	SimpleClient cassandraClient;
	ClientMonior objClientMonior;
	String clientName;
	String mqPortnumber;
	String taskType;
	String taskCount;
	String tastLength;
	String fileSize;
	String filePath;
	boolean monitoringEnabled;
	ConcurrentMap<String, String> mapClientStatus;
	QueueHazelcastUtil objQueueHazelcastUtil;
	long throughputpolltime = 1000;
	public  BatchedClient() {
		super();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			clientName = genUniQID();
			submittedTasks = new ConcurrentHashMap<>();
			Properties properties = new Properties();
			properties.load(reader);
			// hazelClient
			hazelClinetObj = new QueueHazelcastUtil().getClient();
			mapClientStatus = hazelClinetObj.getMap(CLIENT_STATUS);

			numberofWorkerThreads = Long.parseLong(properties
					.getProperty("numberofWorkerThreads"));
			pollTime = Long.parseLong(properties.getProperty("clientPollTime"));
			fileName = properties.getProperty("taskFilePath");
			resouceAllocationMode = properties
					.getProperty("resouceAllocationMode");
			mqPortnumber = properties.getProperty("mqPortnumber");
			taskType = properties.getProperty("taskType");
			taskCount = properties.getProperty("taskCount");
			tastLength = properties.getProperty("tastLength");
			fileSize = properties.getProperty("fileSize");
			filePath = properties.getProperty("filePath");
			monitoringEnabled = properties.getProperty("monitoringEnabled").equals("true");
			throughputpolltime = Long.parseLong(properties.getProperty("monPolltime"));
			if (monitoringEnabled) {
				// Cassandra Client
				String cassServerlist = properties
						.getProperty("cassServerlist");
				cassandraClient = new SimpleClient();
				cassandraClient.connect(cassServerlist);
				// Create monitor
				objClientMonior = new ClientMonior(clientName, cassandraClient,
						submittedTasks, mapClientStatus,hazelClinetObj,throughputpolltime);
			}

		} catch (IOException e) {
			PrintManager.PrintException(e);
		}
	}

	public static void main(String args[]) throws Exception {
		BatchedClient client = new BatchedClient();
		client.postTasks();

	}

	private void postTasks() throws NumberFormatException,
			FileNotFoundException, InterruptedException {
		Thread.currentThread().setPriority(8);
		Semaphore objSemaphore = new Semaphore(0);

		Set<Task> objects = null;
		url = getUrl();
		qu = new QueueDetails(REQUESTQ, RESPONSEQ, clientName, url);
		PrintManager.PrintMessage("creating tasks");
		if (taskType.equalsIgnoreCase(SLEEP_TASK)) {
			objects = makeSleepTasks(clientName);
		} else if (taskType.equalsIgnoreCase(IO_TASK)) {
			objects = makeIOTasks(clientName);
		} else {
			objects = readFileAndMakeTasks(fileName, clientName);
		}
		Set<Task> batchTasks = batchTasks(objects);
		PrintManager.PrintProdMessage("Posting tasks started "
				+ System.nanoTime());
		TaskQueueFactory.getQueue().postTask(objSemaphore, batchTasks, REQUESTQ,
				url, clientName);
		PrintManager.PrintProdMessage("Posting tasks finished "
				+ System.nanoTime());
		String time = String.valueOf(System.nanoTime());
		String[] valFin = { clientName, time, STARTED };
		if (monitoringEnabled) {
			cassandraClient.insertClientStatus(valFin);
		}
		// check the mode of operation
		if (resouceAllocationMode.equals("static")) {
			// Get the already running workers
			long numOfWorkers = WorkerMonitor
					.getNumOfWorkerThreads(hazelClinetObj);
			PrintManager.PrintProdMessage("numOfWorkers " + numOfWorkers);
			if (numOfWorkers == 0) {
				numOfWorkers = 1;
			}
			long loopCount = objects.size() 
					/ (numOfWorkers* numberofWorkerThreads);
			loopCount = loopCount == 0 ? 1 : loopCount;
			PrintManager.PrintProdMessage("Number of Cleint Q Advertizements "
					+ loopCount);
			DistributedQueue queue = QueueFactory.getQueue();
			objSemaphore.acquire();
			for (int loopIndex = 0; loopIndex < loopCount; loopIndex++) {
				queue.pushToQueue(qu);
			}
			PrintManager.PrintProdMessage("Cleint Q Advertizements done");
			mapClientStatus.putIfAbsent(this.clientName + "," + STARTED, time);
		} else {
			// TODO : logic for dynamic allocator where workers wont be stared
			// up front
		}
		// Start monitoring the Submitted Queue length for completion
		new Thread(this).start();
		// Start monitoring the Submitted Queue length for reporting
		if (monitoringEnabled) {
			new Thread(objClientMonior).start();
		}
	}

	private Set<Task> batchTasks(Set<Task> objects) {
		Set<Task> batchTasks = new ConcurrentHashSet<Task>();
		Set<Task> tasks = new LinkedHashSet<Task>();
		for(Task task:objects){
			tasks.add(task);
			if(tasks.size() == numberofWorkerThreads){
				Task batch = new TaskBatch();
				batch.setTasks(tasks);
				batchTasks.add(batch);
				tasks= new LinkedHashSet<Task>();
			}
		}
		if(tasks.size()>0){
			Task batch = new TaskBatch();
			batch.setTasks(tasks);
			batchTasks.add(batch);
			tasks= new LinkedHashSet<Task>();
		}
		return batchTasks;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		while (!submittedTasks.isEmpty()) {
			Task task = TaskQueueFactory.getQueue().retrieveTask(RESPONSEQ,
					url, clientName);
			if (task != null) {
				if (task.isMultiTask()) {
					for (Task tasks : task.getTasks()) {
						PrintManager.PrintMessage("Task[" + tasks.getTaskId()
								+ "]completed");
						submittedTasks.remove(tasks.getTaskId());
					}
				} else {
					PrintManager.PrintMessage("Task[" + task.getTaskId()
							+ "]completed");
					submittedTasks.remove(task.getTaskId());
				}

			}
			// Advertise tasks again after some time
			if (System.currentTimeMillis() - startTime > pollTime) {
				startTime = System.currentTimeMillis();
				DistributedQueue queue = QueueFactory.getQueue();
				queue.pushToQueue(qu);
			}

		}
		if (monitoringEnabled) {
			// Shutdown monitor
			objClientMonior.setClientShutoff(true);
		}
		String time = String.valueOf(System.nanoTime());
		String[] valFin = { clientName, String.valueOf(System.nanoTime()),
				FINISHED };
		mapClientStatus.putIfAbsent(this.clientName + "," + FINISHED, time);
		if (monitoringEnabled) {
			cassandraClient.insertClientStatus(valFin);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				PrintManager.PrintException(e);
			}
		}

		PrintManager.PrintProdMessage("Shutting down Client " + this.clientName
				+ " at " + time);
		// Shutdown hazel
		hazelClinetObj.shutdown();
		if (monitoringEnabled) {
			cassandraClient.close();
		}

	}

	private Set<Task> readFileAndMakeTasks(String fileName, String clientName)
			throws NumberFormatException, FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		Set<Task> list = new LinkedHashSet<Task>();
		Task task = null;
		while (s.hasNext()) {
			task = new TemplateTask(genUniQID() + clientName, clientName,
					RESPONSEQ, url, Long.parseLong(s.next()));
			list.add(task);
			submittedTasks.put(task.getTaskId(), task);
		}
		s.close();
		return list;
	}

	private Set<Task> makeIOTasks(String clientName) {
		int itaskCount = Integer.parseInt(taskCount);
		int counter = 0;
		Set<Task> list = new LinkedHashSet<Task>();
		Task task = null;
		while (counter < itaskCount) {
			task = new TemplateIOTask(genUniQID() + clientName, clientName,
					RESPONSEQ, url, Long.parseLong(fileSize), filePath);
			list.add(task);
			submittedTasks.put(task.getTaskId(), task);
			counter++;
		}
		return list;
	}

	private Set<Task> makeSleepTasks(String clientName) {
		int itaskCount = Integer.parseInt(taskCount);
		int counter = 0;
		Set<Task> list = new LinkedHashSet<Task>();
		Task task = null;
		while (counter < itaskCount) {
			task = new TemplateTask(genUniQID() + clientName, clientName,
					RESPONSEQ, url, Long.parseLong(tastLength));
			list.add(task);
			submittedTasks.put(task.getTaskId(), task);
			counter++;
		}
		return list;
	}

	private String genUniQID() {
		UUID uniqueID = UUIDs.timeBased();
		return uniqueID.toString();
	}

	private String getUrl() {
		InetAddress addr;
		String ipAddress = null;
		try {
			addr = InetAddress.getLocalHost();
			ipAddress = addr.getHostAddress();
		} catch (UnknownHostException e) {
			PrintManager.PrintException(e);
		}
		return "tcp://" + ipAddress + ":" + mqPortnumber;
	}
}
