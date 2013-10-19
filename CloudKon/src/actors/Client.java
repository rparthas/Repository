package actors;

import static utility.Constants.REQUESTQ;
import static utility.Constants.RESPONSEQ;

import java.util.ArrayList;
import java.util.List;

import queue.DistributedQueue;
import queue.QueueFactory;
import utility.QueueUtility;
import entity.QueueDetails;
import entity.Task;
import entity.ThousandSecTask;

public class Client implements Runnable {

	static QueueUtility utility = new QueueUtility();
	static List<Task> submittedTasks = new ArrayList<>();
	static String url = "tcp://localhost:61616";

	public static void main(String args[]) throws Exception {

		QueueDetails qu = new QueueDetails();
		qu.setUrl(url);
		qu.setClientName("client1");
		qu.setRequestQueue(REQUESTQ);
		qu.setResponseQueue(RESPONSEQ);

		List<Task> objects = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Task task = new ThousandSecTask();
			task.clientName = "me";
			task.taskId = i + "";
			task.queueUrl = url;
			task.responseQueueName = RESPONSEQ;
			objects.add(task);
			submittedTasks.add(task);
		}
		utility.postMessage(objects, REQUESTQ, url);
		new Thread(new Client()).start();

		DistributedQueue queue = QueueFactory.getQueue();
		queue.pushToQueue(qu);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!submittedTasks.isEmpty()) {
			Task task = utility.retrieveMessage(RESPONSEQ, url);
			if (task != null) {
				System.out.println("Task[" + task.taskId + "]completed");
				submittedTasks.remove(task);
			}
		}
		System.exit(0);
	}

}
