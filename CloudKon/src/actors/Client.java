package actors;

import static utility.Constants.HAZEL_AN_PORT;
import static utility.Constants.REQUESTQ;
import static utility.Constants.RESPONSEQ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

import monitor.WorkerMonitor;
import queue.DistributedQueue;
import queue.QueueFactory;
import queue.TaskQueueFactory;
import queue.hazelcast.QueueHazelcastUtil;

import com.datastax.driver.core.utils.UUIDs;
import com.hazelcast.client.HazelcastClient;

import entity.QueueDetails;
import entity.Task;
import entity.TemplateTask;

public class Client implements Runnable {

	Map<String, Task> submittedTasks = new HashMap<>();
	String url ;
	QueueDetails qu ;
	long pollTime = 3000;
	
	QueueHazelcastUtil objQueueHazelcastUtil = new QueueHazelcastUtil();
	HazelcastClient hazelClinetObj = objQueueHazelcastUtil.getClient();
	long numIterations=0;
	
	
	
	public Client() {
		super();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			numIterations = Long.parseLong(properties.getProperty("workerIterations"));
			pollTime = Long.parseLong(properties.getProperty("clientPollTime"));
			System.out.println("numIterations "+numIterations);
			System.out.println("clientPollTime "+pollTime);
			
		} catch (IOException e) {
			//TODO remove Exceptin
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		Client client = new Client();
		client.postTasks(args);

	}

	private String getUrl() {
		InetAddress addr;
		String ipAddress=null;
		long port=0;
		try {
			addr = InetAddress.getLocalHost();
			ipAddress = addr.getHostAddress();
			port = hazelClinetObj.getAtomicNumber(HAZEL_AN_PORT).incrementAndGet();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "tcp://"+ipAddress+":"+port;
	}

	private void postTasks(String args[]) throws NumberFormatException,
			FileNotFoundException {
		String clientName =genUniQID();
		url = getUrl();
		qu = new QueueDetails(REQUESTQ, RESPONSEQ, clientName,
				url);
		List<Task> objects = readFileAndMakeTasks(args[0], clientName);
		TaskQueueFactory.getQueue().postTask(objects, REQUESTQ, url);
		new Thread(this).start();
		
		long numOfWorkers = WorkerMonitor.getNumOfWorkerThreads(hazelClinetObj);
		long loopCount = objects.size() / (numOfWorkers*numIterations);
		loopCount = loopCount == 0 ? 1 : loopCount;

		DistributedQueue queue = QueueFactory.getQueue();
		for (int loopIndex = 0; loopIndex < loopCount; loopIndex++) {
			queue.pushToQueue(qu);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		while (!submittedTasks.isEmpty()) {
			// System.out.println("Pending Task length[" + submittedTasks.size()
			// + "]");
			Task task = TaskQueueFactory.getQueue()
					.retrieveTask(RESPONSEQ, url);
			if (task != null) {
				if (task.isMultiTask()) {
					for (Task tasks : task.getTasks()) {
						System.out.println("Task[" + tasks.getTaskId()
								+ "]completed");
						submittedTasks.remove(tasks.getTaskId());
					}
				} else {
					System.out.println("Task[" + task.getTaskId()
							+ "]completed");
					submittedTasks.remove(task.getTaskId());
				}

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
		objQueueHazelcastUtil.closeClient();
		System.exit(0);
	}

	private ArrayList<Task> readFileAndMakeTasks(String fileName,
			String clientName) throws NumberFormatException,
			FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		ArrayList<Task> list = new ArrayList<Task>();
		Task task = null;
		while (s.hasNext()) {
			task = new TemplateTask(genUniQID(), clientName, url, RESPONSEQ,
					Long.parseLong(s.next()));
			list.add(task);
			submittedTasks.put(task.getTaskId(), task);
		}
		s.close();
		return list;
	}

	private String genUniQID() {
		UUID uniqueID = UUIDs.timeBased();
		return uniqueID.toString();
	}
}
