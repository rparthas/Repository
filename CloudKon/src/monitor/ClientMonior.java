package monitor;

import static utility.Constants.QUEUE_LENGTH;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.client.HazelcastClient;

import monitor.cassandra.SimpleClient;
import utility.PrintManager;
import entity.Task;

public class ClientMonior implements Runnable {

	private SimpleClient cassandraClient;
	private Map<String, Task> submittedTasks;
	private String clientID;
	private boolean clientShutoff = false;
	private ConcurrentMap<String, String> mapQLengthStatus;
	private long polltime;
	private HazelcastClient hazelClinetObj;

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
			ConcurrentMap<String, String> mapQLengthStatus,
			HazelcastClient hazelClinetObj, long throughputpolltime) {
		super();
		this.clientID = clientID;
		this.cassandraClient = cassandraClient;
		this.submittedTasks = submittedTasks;
		this.polltime = throughputpolltime;
		this.mapQLengthStatus = mapQLengthStatus;
		this.hazelClinetObj = hazelClinetObj;

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
				long totalQlen = hazelClinetObj.getAtomicNumber(QUEUE_LENGTH)
						.get();
				mapQLengthStatus.put(time, String.valueOf(totalQlen));
				String[] values = { clientID, time, String.valueOf(Qlength) };
				cassandraClient.insertQlength(values);
				PrintManager.PrintProdMessage("Responce Qlength " + Qlength
						+ " " + time);
				PrintManager.PrintProdMessage("Total Submit Qlength "
						+ totalQlen + "," + time);
				Thread.sleep(polltime);
			}

		} catch (InterruptedException e) {
			PrintManager.PrintException(e);
		}
		PrintManager.PrintMessage(" Shutting Client Moniter");
	}

}
