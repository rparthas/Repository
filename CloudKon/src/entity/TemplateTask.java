package entity;

import utility.PrintManager;

public class TemplateTask extends Task {

	public TemplateTask(String taskId, String clientName,
			String responseQueueName, String queueUrl, long sleepTime) {
		super(taskId, clientName, responseQueueName, queueUrl);
		this.sleepTime = sleepTime;
	}

	private long sleepTime = 0;
	
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean call() throws Exception {
		try {
			PrintManager.PrintMessage(this.getClientName()+this.getTaskId()+" sleeping for[" + sleepTime + "] milli secs on "+this.getWorker());
			Thread.sleep(sleepTime);
			return true;
		} catch (InterruptedException e) {
			PrintManager.PrintException(e);
		}
		return false;
	}

	public String toString() {
		return getTaskId();
	}
}
