package queue;

import utility.QueueDetails;


public interface DistributedQueue {

	public void pushToQueue(QueueDetails details);

	public QueueDetails pullFromQueue();

}
