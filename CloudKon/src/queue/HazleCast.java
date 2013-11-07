package queue;

import java.util.List;

import queue.hazelcast.QueueHazelcastUtil;
import utility.Constants;
import utility.PrintManager;
import entity.QueueDetails;
import entity.Task;

public class HazleCast implements DistributedQueue,TaskQueue {

	static QueueHazelcastUtil queueHazelcastUtil = new QueueHazelcastUtil();
	private int currentQCount=0;

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
	public Task retrieveTask(String qName, String url,String clientId) {
		Task task = null;
		try {
			Object obj = queueHazelcastUtil.getObjValue(qName,clientId);
			if (obj != null && obj instanceof Task) {
				task = (Task) obj;
			}
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
		return task;
	}

	@Override
	public void postTask(List<Task> objects, String qName, String url,String clientId) {
		try {
			for(Object object:objects){
				queueHazelcastUtil.putObject(qName,clientId,object);
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
