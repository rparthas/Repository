package actors;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import monitor.WorkerMonitor;
import queue.DistributedQueue;
import queue.QueueFactory;
import queue.TaskQueueFactory;
import queue.hazelcast.QueueHazelcastUtil;

import com.hazelcast.client.HazelcastClient;

import entity.QueueDetails;
import entity.Task;
import entity.TaskBatch;

public class Worker extends TimerTask implements Runnable {
	private QueueHazelcastUtil objQueueHazelcastUtil;
	private HazelcastClient hazelClinetObj;
	private int numberofWorkerThreads = 10;
	private ExecutorService es;
	private Map<String, Integer> waitCounter;
	private Map<String, List<Task>> resultMap;
	private Map<Task, Future<Boolean>> taskMap;
	private int interation;
	// TODO
	private final int batchInterval = 10;

	public Worker() {
		super();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			this.es = Executors.newFixedThreadPool(numberofWorkerThreads);
			// hazelClient
			this.objQueueHazelcastUtil = new QueueHazelcastUtil();
			this.hazelClinetObj = objQueueHazelcastUtil.getClient();
			this.numberofWorkerThreads = Integer.parseInt(properties
					.getProperty("numberofWorkerThreads"));
			this.interation = Integer.parseInt(properties
					.getProperty("interationPerWorker"));

			this.waitCounter = new ConcurrentHashMap<>();
			this.resultMap = new ConcurrentHashMap<>();
			this.taskMap = new ConcurrentHashMap<>();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Main method for starting the worker
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Timer timer = new Timer();
		Worker objWorker = new Worker();
		timer.schedule(objWorker, 0, 2000);
		// Loop never ends once the worker begins execution
		WorkerMonitor.incrNumOfWorkerThreads(objWorker.hazelClinetObj);
		while (true) {
			DistributedQueue queue = QueueFactory.getQueue();
			// Get one Q information
			QueueDetails queueDetails = queue.pullFromQueue();
			int clientCounter;
			// loop for getting the tasks for the client mentioned in Q
			// information we got.
			// iteration per worker
			while (objWorker.interation > 0) {
				// Pulling only tasks for the worker threads
				clientCounter = 0;
				while (clientCounter < objWorker.numberofWorkerThreads) {
					if (queueDetails != null) {
						Task task = TaskQueueFactory.getQueue().retrieveTask(
								queueDetails.getRequestQueue(),
								queueDetails.getUrl());
						if (task != null) {
							// Starting the Task
							Future<Boolean> future = objWorker.es.submit(task);
							objWorker.taskMap.put(task, future);
						}
					}
					clientCounter++;
				}
				objWorker.interation--;
			}
		}
	}

	/**
	 * Timer Method for collating the results
	 */
	@Override
	public void run() {
		for (Task task : taskMap.keySet()) {
			Future<Boolean> future = taskMap.get(task);
			try {
				Boolean result = future.get(1, TimeUnit.SECONDS);
				if (result != null && true == result) {
					taskMap.remove(task);
					addResult(task);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		sendBatchResults();
		if (taskMap.isEmpty() && resultMap.isEmpty()
				&& WorkerMonitor.isTimeLimitReached()) {
			try {
				System.out.println("Terminating the instance");
				WorkerMonitor.decrNumOfWorkerThreads(hazelClinetObj);
				// Runtime.getRuntime().exec("shutdown -h 0");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Sends result back to the client once the task is completed
	 */
	private void sendBatchResults() {
		// TODO Auto-generated method stub
		for (String client : resultMap.keySet()) {
			List<Task> tasks = resultMap.get(client);
			// or expire time
			int counter = 1;
			if (waitCounter.containsKey(client)) {
				counter = waitCounter.get(client) + 1;
			}
			waitCounter.put(client, counter);

			if (tasks != null
					&& (tasks.size() >= numberofWorkerThreads || counter == batchInterval)) {
				Task task = tasks.get(0);
				List<Task> batches = new ArrayList<>();
				Task taskBatch = new TaskBatch();
				taskBatch.setTasks(tasks);
				batches.add(taskBatch);
				TaskQueueFactory.getQueue().postTask(batches,
						task.getResponseQueueName(), task.getQueueUrl());
			}
			resultMap.remove(client);
			waitCounter.remove(client);
		}
	}

	/**
	 * Adds a completed task to the result map to be sent
	 * 
	 * @param task
	 */
	public void addResult(Task task) {
		if (task != null) {
			String clientName = task.getClientName();
			List<Task> tasks = new ArrayList<>();
			if (resultMap.containsKey(clientName)) {
				tasks = resultMap.get(clientName);
			}
			tasks.add(task);
			resultMap.put(clientName, tasks);
		}
	}

}
