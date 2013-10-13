package actors;

import static utility.Constants.REQUESTQ;
import static utility.Constants.RESPONSEQ;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import entity.Task;
import entity.ThousandSecTask;

import queue.DistributedQueue;
import queue.QueueFactory;
import utility.QueueDetails;
import utility.QueueUtility;

public class Client {

	public static void main(String args[]) throws Exception {

		QueueDetails qu = new QueueDetails();
		qu.setUrl("tcp://localhost:61616");
		qu.setClientName("client1");
		qu.setRequestQueue(REQUESTQ);
		qu.setResponseQueue(RESPONSEQ);

		QueueUtility utility = new QueueUtility();
		List<Task> objects = new ArrayList<>();
		for(int i=0;i<10;i++){
			Task task = new ThousandSecTask();
			task.clientName="me";
			task.taskId =i+"";
			task.queueUrl="tcp://localhost:61616";
			task.responseQueueName=RESPONSEQ;
			objects.add(task);
		}
		utility.postMessage(objects, REQUESTQ, "tcp://localhost:61616");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(qu);
		os.close();

		DistributedQueue queue = QueueFactory.getQueue();
		queue.pushToQueue(bos);

	}

}
