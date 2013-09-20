package entity;

public class TemplateTask extends Task {

	public TemplateTask(long sleepTime){
		super();
		this.sleepTime=sleepTime;
	}
	
	long sleepTime =0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("sleeping for["+sleepTime+"]secs");
			Thread.sleep(sleepTime);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	public String toString() {
		return taskId;
	}
}
