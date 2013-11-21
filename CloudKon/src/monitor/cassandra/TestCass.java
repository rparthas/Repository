package monitor.cassandra;

import static utility.Constants.BUSYWORKERCOUNT;
import static utility.Constants.CLIENT_STATUS;
import static utility.Constants.FREEWORKERCOUNT;
import static utility.Constants.HAZEL_NUMWORKERS;
import static utility.Constants.QUEUELENGTH;
import static utility.Constants.QUEUE_LENGTH;
import static utility.Constants.THROUGHPUT_STATUS;
import static utility.Constants.WORKER_COUNT_STATUS;
import static utility.Constants.WORKER_STATUS;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import queue.hazelcast.QueueHazelcastUtil;
import utility.PrintManager;

import com.hazelcast.client.HazelcastClient;

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

			Scanner readinp = new Scanner(System.in);
			boolean breakout = true;
			while (breakout) {
				PrintManager
						.PrintMessage("----------------------------------------------------");
				PrintManager.PrintMessage("Enter your choice");
				PrintManager.PrintMessage("1 ----------> Reset everything");
				PrintManager.PrintMessage("2 ----------> Report Q status");
				PrintManager
						.PrintMessage("3 ----------> Report CPU status for all clients ");
				PrintManager.PrintMessage("4 ----------> Report Client status");
				PrintManager
						.PrintMessage("5 ----------> Report Q Status for a Cleint");
				PrintManager
						.PrintMessage("6 ----------> Report Through Put Status for a Cleint");
				PrintManager
						.PrintMessage("7 ----------> Report worker Count status");
				PrintManager.PrintMessage("8 ----------> Report worker status");
				PrintManager
						.PrintMessage("9 ----------> Report CPU sec wasted");
				PrintManager.PrintMessage("10 ----------> Report CPU sec used");
				PrintManager.PrintMessage("11 ----------> Exit");
				PrintManager
						.PrintMessage("----------------------------------------------------");
				String inp = readinp.nextLine();
				switch (inp) {
				case "1":
					objTestCass.cassandraClient.createSchema();
					mapClientStatus = hazelClinetObj.getMap(CLIENT_STATUS);
					mapClientStatus.clear();
					ConcurrentMap<String, String> mapThroughPutStatus = hazelClinetObj
							.getMap(THROUGHPUT_STATUS);
					ConcurrentMap<String, String> mapWorkerStatus = hazelClinetObj
							.getMap(WORKER_STATUS);
					ConcurrentMap<String, Long> mapWorkerCountStatus = hazelClinetObj
							.getMap(WORKER_COUNT_STATUS);
					ConcurrentMap<String, String> mapQLengthStatus = hazelClinetObj
							.getMap(QUEUELENGTH);
					mapThroughPutStatus.clear();
					mapWorkerStatus.clear();
					mapWorkerCountStatus.clear();
					mapQLengthStatus.clear();
					hazelClinetObj.getAtomicNumber(HAZEL_NUMWORKERS).set(0);
					hazelClinetObj.getAtomicNumber(FREEWORKERCOUNT).set(0);
					hazelClinetObj.getAtomicNumber(BUSYWORKERCOUNT).set(0);
					hazelClinetObj.getAtomicNumber(QUEUE_LENGTH).set(0);
					break;
				case "2":
					mapQLengthStatus = hazelClinetObj.getMap(QUEUELENGTH);
					objTestCass.cassandraClient.getQStatus(mapQLengthStatus);
					break;
				case "3":
					objTestCass.cassandraClient.getRowsCpu();
					break;
				case "4":
					mapClientStatus = hazelClinetObj.getMap(CLIENT_STATUS);
					objTestCass.cassandraClient
							.getClientStatus(mapClientStatus);
					break;
				case "5":
					PrintManager.PrintMessage("Enter Client Name for details");
					String clientName = readinp.nextLine();
					objTestCass.cassandraClient.getQStatus(clientName);
					break;
				case "6":
					mapThroughPutStatus = hazelClinetObj
							.getMap(THROUGHPUT_STATUS);
					objTestCass.cassandraClient
							.getThroughPutStatus(mapThroughPutStatus);

					break;
				case "7":
					mapWorkerCountStatus = hazelClinetObj
							.getMap(WORKER_COUNT_STATUS);
					objTestCass.cassandraClient
							.getWorkerCountStatus(mapWorkerCountStatus);
					break;
				case "8":
					mapWorkerStatus = hazelClinetObj.getMap(WORKER_STATUS);
					objTestCass.cassandraClient
							.getWorkerStatus(mapWorkerStatus);
					break;
				case "9":
					PrintManager.PrintMessage(" WASTED CPU secs :"
							+ hazelClinetObj.getAtomicNumber(FREEWORKERCOUNT)
									.get());
					break;
				case "10":
					PrintManager.PrintMessage(" USED CPU secs :"
							+ hazelClinetObj.getAtomicNumber(BUSYWORKERCOUNT)
									.get());
					break;
				case "11":
					breakout = false;
					break;
				}

			}
			readinp.close();
			objTestCass.cassandraClient.close();
			hazelClinetObj.shutdown();
			objQueueHazelcastUtil.shutdown();
		} catch (IOException e) {
			PrintManager.PrintException(e);
		}
	}
}
