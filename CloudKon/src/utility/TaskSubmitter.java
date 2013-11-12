package utility;

import java.util.Set;
import java.util.concurrent.Semaphore;

import com.hazelcast.core.IQueue;
import com.hazelcast.util.ConcurrentHashSet;

import entity.Task;

public class TaskSubmitter implements  Runnable {

	ConcurrentHashSet<Task> taskList = null;
	private IQueue<Object> clientQ;
	Semaphore objSemaphore;
	public TaskSubmitter(Semaphore objSemaphore, Set<Task> taskList,
			IQueue<Object> clientQ) {
		super();
		this.taskList = (ConcurrentHashSet<Task>) taskList;
		this.clientQ = clientQ;
		this.objSemaphore=objSemaphore;
	}

	public void run() {
		int size=taskList.size();
		int counter =0;
		boolean loclreleased=false;
		for (Object object : taskList) {
			counter++;
			if(size/10<=counter&&!loclreleased){
				objSemaphore.release(1);
				PrintManager.PrintProdMessage("Releasing LOCK");
				loclreleased=true;
			}
			try {
				clientQ.put(object);
				taskList.remove(object);
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}

		}
	}

}
