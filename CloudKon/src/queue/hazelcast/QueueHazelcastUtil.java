package queue.hazelcast;

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
		return client.getQueue(clientId + Qname).poll();
	}

	public void putObject(String master, Object queueDetails)
			throws InterruptedException {
		client.getQueue(master).put(queueDetails);
	}

	public Object getObjValue(String master) throws InterruptedException {
		return client.getQueue(master).take();
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

	public long decrementAndGetAtomicNumber(String atomicnumber) {
		return client.getAtomicNumber(atomicnumber).decrementAndGet();
		
	}
}
