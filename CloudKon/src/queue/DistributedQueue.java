package queue;

import java.io.ByteArrayOutputStream;

import utility.QueueDetails;


public interface DistributedQueue {

	public void pushToQueue(ByteArrayOutputStream bos);

	public QueueDetails pullFromQueue();

}
