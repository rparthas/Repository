package utility;

import actors.Worker;



public class WorkerStarter extends Thread {

	int Workercount;
	public WorkerStarter(int i) {
		Workercount=i;
	}

	public void run(){
		String[] args ={""+Workercount};
		try {
			Worker.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
