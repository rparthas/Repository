package utility;

import actors.Worker;



public class WorkerStarter extends Thread {

	public void run(){
		String[] args =null;
		try {
			Worker.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
