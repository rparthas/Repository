package entity;

import utility.PrintManager;


public class ThousandSecTask extends Task{
	public ThousandSecTask(){
	}
	private static final long serialVersionUID = 1L;
	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		try {
			PrintManager.PrintMessage("sleeping for 1000 secs");
			Thread.sleep(1000);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String toString(){
		return getTaskId();
	}
		
	}
	

