package queue;

import static utility.Constants.QUEUE_LENGTH;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import queue.hazelcast.QueueHazelcastUtil;
import utility.Constants;
import utility.PrintManager;
import utility.TaskSubmitter;

import com.hazelcast.core.IQueue;

import entity.QueueDetails;
import entity.Task;

public class HazleCast implements DistributedQueue, TaskQueue {

	static QueueHazelcastUtil queueHazelcastUtil = new QueueHazelcastUtil();
	private int currentQCount = 0;

	@Override
	public void pushToQueue(QueueDetails queueDetails) {
		try {
			queueHazelcastUtil.putObject(Constants.MASTER, queueDetails);
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
	}

	@Override
	public QueueDetails pullFromQueue() {
		QueueDetails queueDetails = null;
		try {
			Object obj = queueHazelcastUtil.getObjValue(Constants.MASTER);
			if (obj != null && obj instanceof QueueDetails) {
				queueDetails = (QueueDetails) obj;
			}
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
		return queueDetails;
	}

	@Override
	public Task retrieveTask(String qName, String url, String clientId) {
		Task task = null;
		try {
			Object obj = queueHazelcastUtil.getObjValue(qName, clientId);
			if (obj != null && obj instanceof Task) {
				task = (Task) obj;
			}
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
		
		return task;
	}

	@Override
	public void postTask(Semaphore objSemaphore,Set<Task> objects, String qName, String url,
			String clientId) {

		try {
			IQueue<Object> clientQ = queueHazelcastUtil.getQueue(qName,
					clientId);
			if (objects.size() > 1) {
				ExecutorService executor = Executors.newFixedThreadPool(1);
				for (int i = 0; i < 1; i++) {
					Runnable worker = new TaskSubmitter(objSemaphore,objects, clientQ,queueHazelcastUtil,clientId);
					executor.execute(worker);
				}
				executor.shutdown();
			} else {
				for (Task task : objects) {
					clientQ.put(task);
				}
			}

		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
	}

	public int getCurrentQCount() {
		return currentQCount;
	}

	public void setCurrentQCount(int currentQCount) {
		this.currentQCount = currentQCount;
	}

}
