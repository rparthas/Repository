package queue.hazelcast;

import static utility.Constants.MASTER_QUEUE_LENGTH;
import static utility.Constants.QUEUE_LENGTH;
import static utility.Constants.RESPONSEQ;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import utility.PrintManager;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.IQueue;

public class QueueHazelcastUtil {

	private ClientConfig clientConfig;
	private HazelcastClient client;
	public QueueHazelcastUtil() {
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			clientConfig = new ClientConfig();
			Properties properties = new Properties();
			properties.load(reader);
			String serverLoc = properties.getProperty("hazelCastServerList");
			addHazelServerAddress(serverLoc);
			client = HazelcastClient.newHazelcastClient(clientConfig);
		} catch (IOException ex) {
			PrintManager.PrintException(ex);
		}

	}

	public void addHazelServerAddress(String ipAddress_port) {
		String[] splits = ipAddress_port.split(",");
		clientConfig.addAddress(splits);

	}

	public void putObject(String Qname, String clientId, Object Value)
			throws InterruptedException, IOException {
		client.getQueue(clientId + Qname).put(Value);
	}

	public Object getObjValue(String Qname, String clientId)
			throws InterruptedException, IOException {
		Object obj = client.getQueue(clientId + Qname).poll();
		if(obj!=null&&!Qname.equals(RESPONSEQ)){
			client.getAtomicNumber(QUEUE_LENGTH).decrementAndGet();
		}
		return obj;
	}

	public void putObject(String master, Object queueDetails)
			throws InterruptedException {
		client.getQueue(master).put(queueDetails);
		client.getAtomicNumber(MASTER_QUEUE_LENGTH).incrementAndGet();
	}

	public Object getObjValue(String master) throws InterruptedException {
		Object queueInfo =client.getQueue(master).take();
		client.getAtomicNumber(MASTER_QUEUE_LENGTH).decrementAndGet();
		return queueInfo;
	}
	
	public  HazelcastClient getClient() {
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
	public void shutdown(){
		client.shutdown();
	}
	public IQueue<Object> getQueue(String Qname, String clientId)
			throws InterruptedException, IOException {
		return client.getQueue(clientId + Qname);
	}

}
