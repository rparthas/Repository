package queue;

public class TaskQueueFactory {

	public static TaskQueue getQueue(){
		TaskQueue queue = null;
		queue = new HazleCast();
		//queue = new ActiveMQ();
		return queue;
	}
}
