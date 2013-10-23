package entity;

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
		// TODO Auto-generated method stub
		try {
			System.out.println("sleeping for[" + sleepTime + "]secs");
			Thread.sleep(sleepTime);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String toString() {
		return getTaskId();
	}
}
