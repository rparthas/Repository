package utility;
import static utility.Constants.QUEUE_LENGTH;
import java.util.Set;
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
	public TaskSubmitter(Semaphore objSemaphore, Set<Task> taskList,
			IQueue<Object> clientQ, QueueHazelcastUtil queueHazelcastUtil) {
		super();
		this.taskList = (ConcurrentHashSet<Task>) taskList;
		this.clientQ = clientQ;
		this.objSemaphore=objSemaphore;
		this.queueHazelcastUtil=queueHazelcastUtil;
	}

	public void run() {
		int size=taskList.size();
		int counter =0;
		HazelcastClient hazelClient = queueHazelcastUtil.getClient();
		boolean loclreleased=false;
		for (Object object : taskList) {
			hazelClient.getAtomicNumber(QUEUE_LENGTH).incrementAndGet();
			counter++;
			if(size/10<=counter&&!loclreleased){
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
	}

}
