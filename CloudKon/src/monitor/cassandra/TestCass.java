package monitor.cassandra;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;

public class TestCass  {
	SimpleClient cassandraClient;
	final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss:z");

	public static void main(String[] args) throws Exception {
		TestCass objTestCass = new TestCass();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			String cassServerlist = properties.getProperty("cassServerlist");
			objTestCass.cassandraClient = new SimpleClient();
			objTestCass.cassandraClient.connect(cassServerlist);
			System.out.println(cassServerlist);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner readinp = new Scanner(System.in);
		boolean breakout=true;
		while (breakout){
			System.out.println("----------------------------------------------------");
			System.out.println("Enter your choice");
			System.out.println("1 ----------> Reset everything");
			System.out.println("2 ----------> Report Q status");
			System.out.println("3 ----------> Report CPU status");
			System.out.println("4 ----------> Report Client status");
			System.out.println("5 ----------> Exit");
			System.out.println("----------------------------------------------------");
			String inp = readinp.nextLine();
			switch (inp){
			case "1" : objTestCass.cassandraClient.createSchema();
				      break;
			case "2" :objTestCass.cassandraClient.getQStatus();
				      break;
			case "3" :objTestCass.cassandraClient.getRowsCpu();
				      break;
			case "4" :objTestCass.cassandraClient.getClientStatus();
				      break;
			case "5" :breakout=false;break;
			}
			
		}
		readinp.close();
		objTestCass.cassandraClient.close();
	}
}
