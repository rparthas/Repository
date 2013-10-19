package queue;

import queue.hazelcast.QueueHazelcastUtil;
import utility.Constants;
import entity.QueueDetails;

public class HazleCast implements DistributedQueue {

	QueueHazelcastUtil queueHazelcastUtil = new QueueHazelcastUtil();

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

}
