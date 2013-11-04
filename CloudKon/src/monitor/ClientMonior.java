package monitor;

import static utility.Constants.FINISHED;
import static utility.Constants.STARTED;

import java.util.Date;
import java.util.Map;

import monitor.cassandra.SimpleClient;
import utility.PrintManager;
import entity.Task;

public class ClientMonior implements Runnable {

	private SimpleClient cassandraClient;
	private Map<String, Task> submittedTasks;
	private String clientID;
	private boolean clientShutoff = false;

	public SimpleClient getCassandraClient() {
		return cassandraClient;
	}

	public void setCassandraClient(SimpleClient cassandraClient) {
		this.cassandraClient = cassandraClient;
	}

	public Map<String, Task> getSubmittedTasks() {
		return submittedTasks;
	}

	public void setSubmittedTasks(Map<String, Task> submittedTasks) {
		this.submittedTasks = submittedTasks;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public boolean isClientShutoff() {
		return clientShutoff;
	}

	public void setClientShutoff(boolean clientShutoff) {
		this.clientShutoff = clientShutoff;
	}

	public ClientMonior(String clientID, SimpleClient cassandraClient,
			Map<String, Task> submittedTasks) {
		super();
		this.clientID = clientID;
		this.cassandraClient = cassandraClient;
		this.submittedTasks = submittedTasks;

	}

	public static void main(String[] args) {

	}

	@Override
	public void run() {
		boolean isStartTimerecorded =false;
		boolean isEndTimeRecorded =false;
		int Qlength=0;
		try {
			while (!clientShutoff) {
				Qlength=submittedTasks.size();
				if(!isStartTimerecorded&&Qlength>=1){
					isStartTimerecorded=true;
					//INSERT CODE TO RECROD START TIME
					String[] values = { clientID,
							String.valueOf(System.nanoTime()),
							STARTED };
					cassandraClient.insertClientStatus(values);
					PrintManager.PrintMessage("RECROD START TIME");
				}
				String[] values = { clientID,
						WorkerMonitor.getTimestamp(new Date()),
						String.valueOf(Qlength) };
				cassandraClient.insertQlength(values);
				
				if(isStartTimerecorded&&Qlength==0){
					isEndTimeRecorded=true;
					String[] valFin = { clientID,
							String.valueOf(System.nanoTime()),
							FINISHED };
					cassandraClient.insertClientStatus(valFin);
					PrintManager.PrintMessage("RECROD END TIME");
				}
				Thread.sleep(1);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!isEndTimeRecorded){
			String[] valFin = { clientID,
					String.valueOf(System.nanoTime()),
					FINISHED };
			cassandraClient.insertClientStatus(valFin);
		}
		PrintManager.PrintMessage(" Shutting Client Moniter");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
