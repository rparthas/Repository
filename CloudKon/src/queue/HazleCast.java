package queue;

import java.util.List;

import queue.hazelcast.QueueHazelcastUtil;
import utility.Constants;
import entity.QueueDetails;
import entity.Task;

public class HazleCast implements DistributedQueue,TaskQueue {

	static QueueHazelcastUtil queueHazelcastUtil = new QueueHazelcastUtil();

	@Override
	public void pushToQueue(QueueDetails queueDetails) {
		// TODO Auto-generated method stub
		try {
			queueHazelcastUtil.putObject(Constants.MASTER, queueDetails);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public QueueDetails pullFromQueue() {
		// TODO Auto-generated method stub
		QueueDetails queueDetails = null;
		try {
			Object obj = queueHazelcastUtil.getObjValue(Constants.MASTER);
			if (obj != null && obj instanceof QueueDetails) {
				queueDetails = (QueueDetails) obj;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queueDetails;
	}

	@Override
	public Task retrieveTask(String qName, String url) {
		// TODO Auto-generated method stub
		Task task = null;
		try {
			Object obj = queueHazelcastUtil.getObjValue(qName);
			if (obj != null && obj instanceof Task) {
				task = (Task) obj;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public void postTask(List<Task> objects, String qName, String url) {
		// TODO Auto-generated method stub
		try {
			for(Object object:objects){
				queueHazelcastUtil.putObject(qName,object);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
