package utility;
import static utility.Constants.CLIENT_STATUS;
import static utility.Constants.FINISHED_SUBMISTION;
import static utility.Constants.QUEUE_LENGTH;

import java.io.FileReader;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

import queue.hazelcast.QueueHazelcastUtil;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.IQueue;
import com.hazelcast.util.ConcurrentHashSet;

import entity.Task;

public class TaskSubmitter implements  Runnable {

	ConcurrentHashSet<Task> taskList = null;
	private IQueue<Object> clientQ;
	Semaphore objSemaphore;
	private QueueHazelcastUtil queueHazelcastUtil;
	private String clientId;
	private float percentage;
	public TaskSubmitter(Semaphore objSemaphore, Set<Task> taskList,
			IQueue<Object> clientQ, QueueHazelcastUtil queueHazelcastUtil,String clientId) {
		super();
		this.taskList = (ConcurrentHashSet<Task>) taskList;
		this.clientQ = clientQ;
		this.objSemaphore=objSemaphore;
		this.queueHazelcastUtil=queueHazelcastUtil;
		this.clientId=clientId;
		FileReader reader;
		try {
			reader = new FileReader("CloudKon.properties");
		
		Properties properties = new Properties();
		properties.load(reader);
		this.percentage = (Float.parseFloat(properties
				.getProperty("PercentageBefAdvertize"))/100);
		} catch (Exception e) {
			
		}
	}

	public void run() {
		int size=taskList.size();
		int counter =0;
		ConcurrentMap<String, String> mapClientStatus;
		HazelcastClient hazelClient = queueHazelcastUtil.getClient();
		mapClientStatus = hazelClient.getMap(CLIENT_STATUS);
		boolean loclreleased=false;
		for (Object object : taskList) {
			hazelClient.getAtomicNumber(QUEUE_LENGTH).incrementAndGet();
			counter++;
			if( (size*percentage )<=counter&&!loclreleased){
				objSemaphore.release(1);
				PrintManager.PrintProdMessage("Releasing LOCK");
				loclreleased=true;
			}
			try {
				clientQ.put(object);
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}

		}
		mapClientStatus.putIfAbsent(clientId + "," + FINISHED_SUBMISTION, String.valueOf(System.nanoTime()));
	}

}
