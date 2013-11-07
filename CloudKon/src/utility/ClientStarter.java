package utility;

import actors.Client;



public class ClientStarter extends Thread {

	public void run(){
		String[] args =null;
		try {
			Client.main(args);
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
	}

}
