package monitor.cassandra;

import static utility.Constants.CLIENT_STATUS;

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
			QueueHazelcastUtil objQueueHazelcastUtil = new QueueHazelcastUtil();
			HazelcastClient hazelClinetObj = objQueueHazelcastUtil.getClient();
			mapClientStatus = hazelClinetObj.getMap(CLIENT_STATUS);

			Scanner readinp = new Scanner(System.in);
			boolean breakout = true;
			while (breakout) {
				PrintManager
						.PrintMessage("----------------------------------------------------");
				PrintManager.PrintMessage("Enter your choice");
				PrintManager.PrintMessage("1 ----------> Reset everything");
				PrintManager.PrintMessage("2 ----------> Report Q status");
				PrintManager.PrintMessage("3 ----------> Report CPU status");
				PrintManager.PrintMessage("4 ----------> Report Client status");
				PrintManager.PrintMessage("5 ----------> Exit");
				PrintManager
						.PrintMessage("----------------------------------------------------");
				String inp = readinp.nextLine();
				switch (inp) {
				case "1":
					objTestCass.cassandraClient.createSchema();
					mapClientStatus.clear();
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
					breakout = false;
					break;
				}

			}
			readinp.close();
			objTestCass.cassandraClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
