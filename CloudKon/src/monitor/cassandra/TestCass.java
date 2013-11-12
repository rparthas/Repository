package monitor.cassandra;

import static utility.Constants.CLIENT_STATUS;
import static utility.Constants.HAZEL_NUMWORKERS;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.client.HazelcastClient;

import queue.hazelcast.QueueHazelcastUtil;

import utility.PrintManager;

public class TestCass {
	SimpleClient cassandraClient;
	final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss:z");

	public static void main(String[] args) throws Exception {
		TestCass objTestCass = new TestCass();
		ConcurrentMap<String, String> mapClientStatus = null;
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			String cassServerlist = properties.getProperty("cassServerlist");
			objTestCass.cassandraClient = new SimpleClient();
			objTestCass.cassandraClient.connect(cassServerlist);
			PrintManager.PrintMessage(cassServerlist);

			// hazelClient
			HazelcastClient hazelClinetObj = QueueHazelcastUtil.getClient();
			mapClientStatus = hazelClinetObj.getMap(CLIENT_STATUS);

			Scanner readinp = new Scanner(System.in);
			boolean breakout = true;
			while (breakout) {
				PrintManager
						.PrintMessage("----------------------------------------------------");
				PrintManager.PrintMessage("Enter your choice");
				PrintManager.PrintMessage("1 ----------> Reset everything");
				PrintManager.PrintMessage("2 ----------> Report Q status");
				PrintManager.PrintMessage("3 ----------> Report CPU status for all clients ");
				PrintManager.PrintMessage("4 ----------> Report Client status");
				PrintManager.PrintMessage("5 ----------> Report Q Status for a Cleint");
				PrintManager.PrintMessage("6 ----------> Exit");
				PrintManager
						.PrintMessage("----------------------------------------------------");
				String inp = readinp.nextLine();
				switch (inp) {
				case "1":
					objTestCass.cassandraClient.createSchema();
					mapClientStatus.clear();
					hazelClinetObj.getAtomicNumber(HAZEL_NUMWORKERS).set(0);
					break;
				case "2":
					objTestCass.cassandraClient.getQStatus();
					break;
				case "3":
					objTestCass.cassandraClient.getRowsCpu();
					break;
				case "4":
					objTestCass.cassandraClient.getClientStatus(mapClientStatus);
					break;
				case "5":
					PrintManager.PrintMessage("Enter Client Name for details");
					String clientName =readinp.nextLine();
					objTestCass.cassandraClient.getQStatus(clientName);
					break;
				case "6":
					breakout = false;
					break;
				}

			}
			readinp.close();
			objTestCass.cassandraClient.close();
			hazelClinetObj.shutdown();
		} catch (IOException e) {
			PrintManager.PrintException(e);
		}
	}
}
