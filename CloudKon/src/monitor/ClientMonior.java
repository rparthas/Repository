package monitor;

import java.util.Date;
import java.util.Map;
import static utility.Constants.STARTED;
import static utility.Constants.FINISHED;

import monitor.cassandra.SimpleClient;
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
		try {
			while (!clientShutoff) {
				if(!isStartTimerecorded&&submittedTasks.size()>0){
					isStartTimerecorded=true;
					//INSERT CODE TO RECROD START TIME
					String[] values = { clientID,
							WorkerMonitor.getTimestamp(new Date()),
							STARTED };
					cassandraClient.insertClientStatus(values);
				}
				String[] values = { clientID,
						WorkerMonitor.getTimestamp(new Date()),
						String.valueOf(submittedTasks.size()) };
				cassandraClient.insertQlength(values);
				
				if(isStartTimerecorded&&submittedTasks.size()==0){
					isStartTimerecorded=true;
					String[] valFin = { clientID,
							WorkerMonitor.getTimestamp(new Date()),
							FINISHED };
					cassandraClient.insertClientStatus(valFin);
					//INSERT CODE TO RECROD END TIME
				}
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Shutting Client Moniter");

	}
}
