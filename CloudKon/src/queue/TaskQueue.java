package queue;

import java.util.List;
import java.util.Set;

import entity.Task;

public interface TaskQueue {
	
	public Task retrieveTask(String qName, String url,String clientId) ;
	
	public void postTask(Set<Task> objects, String qName, String url,String clientId) ;

}
