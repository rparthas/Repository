package queue;

import java.util.Set;
import java.util.concurrent.Semaphore;

import entity.Task;

public interface TaskQueue {
	
	public Task retrieveTask(String qName, String url,String clientId) ;
	
	public void postTask(Semaphore objSemaphore, Set<Task> objects, String qName, String url,String clientId) ;

}
