package entity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Task implements Serializable,Callable<Boolean> {

	private String taskId;
	
	private List<Task> tasks;
	
	private boolean isMultiTask=false;
	
	private static final long serialVersionUID = -2392409082449596204L;
	
	private String clientName;
	
	private String responseQueueName;
	
	private String queueUrl;
	public Task(){
		
	}
	
	public Task(String taskId, String clientName, String responseQueueName,
			String queueUrl) {
		super();
		this.taskId = taskId;
		this.clientName = clientName;
		this.responseQueueName = responseQueueName;
		this.queueUrl = queueUrl;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public boolean isMultiTask() {
		return isMultiTask;
	}

	public void setMultiTask(boolean isMultiTask) {
		this.isMultiTask = isMultiTask;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getResponseQueueName() {
		return responseQueueName;
	}

	public void setResponseQueueName(String responseQueueName) {
		this.responseQueueName = responseQueueName;
	}

	public String getQueueUrl() {
		return queueUrl;
	}

	public void setQueueUrl(String queueUrl) {
		this.queueUrl = queueUrl;
	}

	public boolean equals(Task task){
		return taskId.equals(task.taskId);
	}
	
	public String toString(){
		return taskId;
	}
}
