package entity;

import java.io.Serializable;
import java.util.concurrent.Callable;

public abstract class Task implements Serializable,Callable<Boolean> {

	/**
	 * 
	 */
	public String taskId;
	
	private static final long serialVersionUID = -2392409082449596204L;
	
	public String clientName;
	
	public String responseQueueName;
	
	public String queueUrl;

}
