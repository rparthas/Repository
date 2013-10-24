package queue.hazelcast;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;

public class QueueHazelcastUtil {

	private ClientConfig clientConfig;
	private HazelcastClient client;
	private BlockingQueue<Object> customQobj;
	private BlockingQueue<String> customQstr;
	private String strQname;
	private String objQname;

	public QueueHazelcastUtil()  {
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			this.clientConfig = new ClientConfig();
			Properties properties = new Properties();
			properties.load(reader);
			String serverLoc = properties.getProperty("hazelCastServerList");
			addHazelServerAddress(serverLoc);
		}catch( IOException ex){
			//TODO remove ex.print
			ex.printStackTrace();
		}
		
	}

	public void addHazelServerAddress(String ipAddress_port) {
		clientConfig.addAddress(ipAddress_port);
	}

	public void putString(String Qname, String Value)
			throws InterruptedException, IOException {
		if (customQstr == null) {
			getStrQueue(Qname);
			customQstr.put(Value);
		} else if (customQstr != null && strQname != null
				&& strQname.equals(Qname)) {
			customQstr.put(Value);
		} else {
			throw new IOException("Qname mismatch");
		}

	}

	public void putObject(String Qname, Object Value)
			throws InterruptedException, IOException {
		if (customQobj == null) {
			getObjectQueue(Qname);
			customQobj.put(Value);
		} else if (customQobj != null && objQname != null
				&& objQname.equals(Qname)) {
			customQobj.put(Value);
		} else {
			throw new IOException("Qname mismatch");
		}

	}

	public String getStrValue(String Qname) throws InterruptedException,
			IOException {
		String strValue = null;
		if (customQstr == null) {
			getStrQueue(Qname);
			strValue = customQstr.take();
		} else if (customQstr != null && strQname != null
				&& strQname.equals(Qname)) {
			strValue = customQstr.take();
		} else {
			throw new IOException(
					"Qname mismatch or No Queue by that name present");
		}
		return strValue;
	}

	public Object getObjValue(String Qname) throws InterruptedException,
			IOException {
		Object objValue = null;
		if (customQobj == null) {
			getObjectQueue(Qname);
			objValue = customQobj.take();
		} else if (customQobj != null && objQname != null
				&& objQname.equals(Qname)) {
			objValue = customQobj.take();
		} else {
			throw new IOException(
					"Qname mismatch or No Queue by that name present");
		}
		return objValue;
	}

	public void closeClient() {
		HazelcastClient.shutdownAll();
		clientConfig = null;
		client = null;
		customQobj = null;
		customQstr = null;
	}

	/**
	 * For joining Ec2 cluster make sure that you are executing it from within
	 * ec2 nodes.
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		QueueHazelcastUtil objQueueHazelcastUtil = new QueueHazelcastUtil();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			Scanner scanner = new Scanner(System.in);
			properties.load(reader);
			String serverLoc = properties.getProperty("hazelCastServerList");
			objQueueHazelcastUtil.addHazelServerAddress(serverLoc);
			boolean keeprunning = true;
			while (keeprunning) {
				System.out
						.println("Enter 1 for putting a value into Q  and 2 for taking and 3 for Closing");
				String input = scanner.nextLine();
				if (input.equals("1")) {
					System.out.println("Enter Q name");
					String qname = scanner.nextLine();
					System.out.println("Enter value");
					String value = scanner.nextLine();
					objQueueHazelcastUtil.putString(qname, value);
				} else if (input.equals("2")) {
					System.out.println("Enter Q name");
					String qname = scanner.nextLine();
					String value = objQueueHazelcastUtil.getStrValue(qname);
					System.out.println(value);
				} else {
					keeprunning = false;
					scanner.close();
					objQueueHazelcastUtil.closeClient();
					objQueueHazelcastUtil = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HazelcastClient getClient() {
		client = HazelcastClient.newHazelcastClient(clientConfig);
		return client;
	}

	private BlockingQueue<Object> getObjectQueue(String queuename) {
		if (client == null) {
			getClient();
		}
		customQobj = client.getQueue(queuename);
		objQname = queuename;
		return customQobj;
	}

	private BlockingQueue<String> getStrQueue(String queuename) {
		if (client == null) {
			getClient();
		}
		customQstr = client.getQueue(queuename);
		strQname = queuename;
		return customQstr;
	}

}
