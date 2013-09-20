package queue;

import java.util.List;

import entity.Task;

public interface TaskQueue {
	
	public Task retrieveTask(String qName, String url) ;
	
	public void postTask(List<Task> objects, String qName, String url) ;

}
