package utility;

import actors.WorkerNew;



public class WorkerStarter extends Thread {

	int Workercount;
	public WorkerStarter(int i) {
		Workercount=i;
	}

	public void run(){
		String[] args ={""+Workercount};
		try {
			WorkerNew.main(args);
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
	}

}
