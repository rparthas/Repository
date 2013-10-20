package entity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Task implements Serializable,Callable<Boolean> {

	/**
	 * 
	 */
	public String taskId;
	
	public List<Task> tasks;
	
	public boolean isMultiTask=false;
	
	private static final long serialVersionUID = -2392409082449596204L;
	
	public String clientName;
	
	public String responseQueueName;
	
	public String queueUrl;
	
	public boolean equals(Task task){
		return taskId.equals(task.taskId);
	}
	
	public String toString(){
		return taskId;
	}
}
