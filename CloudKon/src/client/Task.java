package client;

import java.io.Serializable;

public class Task implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(){
		try {
			System.out.println("sleeping for 1000 secs");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
