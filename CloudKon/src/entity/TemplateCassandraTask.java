package entity;

import com.sun.mail.iap.ByteArray;

import monitor.cassandra.SimpleClient;
import utility.PrintManager;

public class TemplateCassandraTask extends Task {

	public TemplateCassandraTask(String taskId, String clientName,
			String responseQueueName, String queueUrl, long fileSize,SimpleClient cassandraClient) {
		super(taskId, clientName, responseQueueName, queueUrl);
		this.fileSize = fileSize;
		this.cassandraClient = cassandraClient;
	}

	private long fileSize = 0;
	SimpleClient cassandraClient=null;
	
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean call() throws Exception {
		try {
			PrintManager.PrintMessage(this.getTaskId()+" Inserting Data of  [" + fileSize + "] Bytes in Cassandra");
			//cassandraClient.insertData(new ByteArray((int) fileSize));
            return true;
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
		return false;
	}

	public String toString() {
		return getTaskId();
	}
}
