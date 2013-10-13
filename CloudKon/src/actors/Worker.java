package actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import queue.DistributedQueue;
import queue.QueueFactory;
import utility.QueueDetails;
import utility.QueueUtility;
import entity.Task;

public class Worker extends TimerTask {

	static QueueUtility utility = new QueueUtility();
	Map<String, List<Task>> results = new HashMap<>();
	static int poolSize = 10;
	static ExecutorService es = Executors.newFixedThreadPool(poolSize);
	static Map<String, Integer> waitCounter = new ConcurrentHashMap<>();
	static Map<String, List<Task>> resultMap = new ConcurrentHashMap<>();
	static Map<Task, Future<Boolean>> taskMap = new ConcurrentHashMap<>();
	static final int batchInterval = 10;

	/**
	 * Main method for starting the worker
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Timer timer = new Timer();
		timer.schedule(new Worker(), 0, 2000);

		/**
		 * Loop never ends once the worker begins execution
		 */
		while (true) {

			DistributedQueue queue = QueueFactory.getQueue();
			QueueDetails queueDetails = queue.pullFromQueue();

			int clientCounter = 0;
			/**
			 * loop for getting the tasks
			 */
			while (clientCounter < 10) {

				if(queueDetails!=null){
					Task task = utility.retrieveMessage(
							queueDetails.getRequestQueue(), queueDetails.getUrl());
					if (task != null) {
						Future<Boolean> future = es.submit(task);
						taskMap.put(task, future);
	
					}
				}
				clientCounter++;
			}

		}

	}

	/**
	 * Timer Method for collating the results
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		if (taskMap.isEmpty() && resultMap.isEmpty()) {
			try {
				System.out.println("Terminating the instance");
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
			/**
			 * or expiry time
			 */
			int counter = 1;
			if (waitCounter.containsKey(client)) {
				counter = waitCounter.get(client) + 1;
			}
			waitCounter.put(client, counter);

			if (tasks != null
					&& (tasks.size() >= 10 || counter == batchInterval)) {
				Task task = tasks.get(0);
				utility.postMessage(tasks, task.responseQueueName,
						task.queueUrl);
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
			String clientName = task.clientName;
			List<Task> tasks = new ArrayList<>();
			if (resultMap.containsKey(clientName)) {
				tasks = resultMap.get(clientName);
			}
			tasks.add(task);
			resultMap.put(clientName, tasks);
		}
	}

}
