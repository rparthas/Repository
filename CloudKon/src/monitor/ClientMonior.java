package monitor;

import static utility.Constants.FINISHED;
import static utility.Constants.STARTED;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import monitor.cassandra.SimpleClient;
import utility.PrintManager;
import entity.Task;

public class ClientMonior implements Runnable {

	private SimpleClient cassandraClient;
	private Map<String, Task> submittedTasks;
	private String clientID;
	private boolean clientShutoff = false;
	private ConcurrentMap<String, String> mapClientStatus;
	private long polltime;

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
			Map<String, Task> submittedTasks,
			ConcurrentMap<String, String> mapClientStatus, long throughputpolltime) {
		super();
		this.clientID = clientID;
		this.cassandraClient = cassandraClient;
		this.submittedTasks = submittedTasks;
		this.polltime = throughputpolltime;
		this.setMapClientStatus(mapClientStatus);

	}

	public static void main(String[] args) {

	}

	@Override
	public void run() {
		int Qlength = 0;
		try {
			while (!clientShutoff) {
				Qlength = submittedTasks.size();
				String time = String.valueOf(System.nanoTime());
				String[] values = { clientID,
						time,
						String.valueOf(Qlength) };
				cassandraClient.insertQlength(values);
				PrintManager.PrintProdMessage("Qlength "+Qlength +" " +time);
				Thread.sleep(polltime);
			}

		} catch (InterruptedException e) {
			PrintManager.PrintException(e);
		}
		PrintManager.PrintMessage(" Shutting Client Moniter");
	}

	public Map<String, String> getMapClientStatus() {
		return mapClientStatus;
	}

	public void setMapClientStatus(ConcurrentMap<String, String> mapClientStatus) {
		this.mapClientStatus = mapClientStatus;
	}
}
