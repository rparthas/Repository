package queue;

import entity.QueueDetails;


public interface DistributedQueue {

	public void pushToQueue(QueueDetails details);

	public QueueDetails pullFromQueue();

}
