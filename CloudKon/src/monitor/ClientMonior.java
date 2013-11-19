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
			ConcurrentMap<String, String> mapClientStatus) {
		super();
		this.clientID = clientID;
		this.cassandraClient = cassandraClient;
		this.submittedTasks = submittedTasks;
		this.setMapClientStatus(mapClientStatus);

	}

	public static void main(String[] args) {

	}

	@Override
	public void run() {
		boolean isStartTimerecorded = false;
		boolean isEndTimeRecorded = false;
		int Qlength = 0;
		try {
			while (!clientShutoff) {
				Qlength = submittedTasks.size();
				if (!isStartTimerecorded && Qlength >= 1) {
					isStartTimerecorded = true;
					String time = String.valueOf(System.nanoTime());
					String[] values = { clientID, time, STARTED };
					cassandraClient.insertClientStatus(values);
					mapClientStatus.putIfAbsent(clientID + "," + STARTED, time);
					PrintManager.PrintMessage("RECROD START TIME");
				}
				String time = String.valueOf(System.nanoTime());
				String[] values = { clientID,
						time,
						String.valueOf(Qlength) };
				cassandraClient.insertQlength(values);

				if (isStartTimerecorded && Qlength == 0) {
					isEndTimeRecorded = true;
					time = String.valueOf(System.nanoTime());
					String[] valFin = { clientID, time, FINISHED };
					mapClientStatus
							.putIfAbsent(clientID + "," + FINISHED, time);
					cassandraClient.insertClientStatus(valFin);
					PrintManager.PrintMessage("RECROD END TIME");
				}
				Thread.sleep(1000);
			}

		} catch (InterruptedException e) {
			PrintManager.PrintException(e);
		}
		if (!isEndTimeRecorded) {
			String time = String.valueOf(System.nanoTime());
			String[] valFin = { clientID, time, FINISHED };
			mapClientStatus.putIfAbsent(clientID + "," + FINISHED, time);
			cassandraClient.insertClientStatus(valFin);
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
