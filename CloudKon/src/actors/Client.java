package actors;

import static utility.Constants.REQUESTQ;
import static utility.Constants.RESPONSEQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import monitor.WorkerMonitor;
import queue.DistributedQueue;
import queue.QueueFactory;
import queue.TaskQueueFactory;
import entity.QueueDetails;
import entity.Task;
import entity.TemplateTask;

public class Client implements Runnable {

	Map<String, Task> submittedTasks = new HashMap<>();
	String url = "tcp://localhost:61616";
	QueueDetails qu = new QueueDetails();
	long pollTime = 3000;

	public static void main(String args[]) throws Exception {
		Client client = new Client();
		client.postTasks();

	}

	private void postTasks() {
		Scanner scanner = new Scanner(System.in);
		String clientName = getInput(scanner, "Enter client name");
		// url = getInput(scanner, "Enter the ActiveMQ url");
		int taskCount = 0;
		long sleepTime = 0;
		// String value ="N";
		// do{
		while (true) {
			String taskCnt = getInput(scanner, "Enter task count");
			try {
				taskCount = Integer.parseInt(taskCnt);
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (true) {
			String time = getInput(scanner, "Enter sleep Time");
			try {
				sleepTime = Long.parseLong(time);
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		qu.setUrl(url);
		qu.setClientName(clientName);
		qu.setRequestQueue(REQUESTQ);
		qu.setResponseQueue(RESPONSEQ);

		List<Task> objects = new ArrayList<>();
		for (int i = 0; i < taskCount; i++) {
			Task task = new TemplateTask(sleepTime);
			task.clientName = clientName;
			task.taskId = clientName + ":" + System.nanoTime();
			task.queueUrl = url;
			task.responseQueueName = RESPONSEQ;
			objects.add(task);
			submittedTasks.put(task.taskId, task);
		}
		TaskQueueFactory.getQueue().postTask(objects, REQUESTQ, url);
		new Thread(this).start();

		int numOfWorkers = WorkerMonitor.getNumOfWorkerThreads();
		int loopCount = objects.size() / numOfWorkers;
		loopCount = loopCount == 0 ? 1 : loopCount;

		for (int loopIndex = 0; loopIndex < loopCount; loopIndex++) {
			DistributedQueue queue = QueueFactory.getQueue();
			queue.pushToQueue(qu);
		}

		// value= getInput(scanner,
		// "Do you want to add more tasks ? Please Enter Y/N");
		// while(!"Y".equals(value) && !"N".equals(value)){
		// value= getInput(scanner,
		// "Do you want to add more tasks ? Please Enter Y/N");
		// }
		// }while("Y".equals(value));

	}

	private static String getInput(Scanner scanner, String text) {
		String input = null;
		while (input == null || "".equals(input.trim())) {
			System.out.println(text);
			input = scanner.nextLine();
		}
		return input;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		while (!submittedTasks.isEmpty()) {
			//System.out.println("Pending Task length[" + submittedTasks.size()
				//	+ "]");
			Task task = TaskQueueFactory.getQueue()
					.retrieveTask(RESPONSEQ, url);
			if (task != null) {
				System.out.println("Task[" + task.taskId + "]completed");
				submittedTasks.remove(task.taskId);
			}
			/**
			 * Advertise tasks again after some time
			 */
			if (System.currentTimeMillis() - startTime > pollTime) {
				startTime = System.currentTimeMillis();
				DistributedQueue queue = QueueFactory.getQueue();
				queue.pushToQueue(qu);
			}

		}
		System.exit(0);
	}

}
