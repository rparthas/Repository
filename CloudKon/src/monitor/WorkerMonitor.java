package monitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import monitor.cassandra.SimpleClient;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;

public class WorkerMonitor implements Runnable {

	/**
	 * @param args
	 */
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:z");
	

	final static long offsetInMilliseconds = 1000 * 60 * 2;
	private  String cassServerlist = "127.0.0.1";
	SimpleClient cassandraClient;
	public WorkerMonitor(String strcassServerlist) {
		super();
		cassServerlist = strcassServerlist;
	}
	public static void main(String[] args) {
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			String url = properties.getProperty("cassServerlist");
			System.out.println(url);
			new Thread(new WorkerMonitor(url)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider().getCredentials();
		//AWSCredentials credentials = new BasicAWSCredentials("AKIAISAKBFD5OKP3GJTA", "VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");
		cassandraClient = new SimpleClient();
		cassandraClient.connect(cassServerlist);
		String whoAmI;
		whoAmI = retrieveInstanceId();
		// TODO remove Hard coding
		// whoAmI = "i-6e9c5f5b";
		while (true) {
			try {
				System.out.println("-----------------------------------------------");
				monitorInstance(credentials, whoAmI);
				Thread.sleep(1000 * 60);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				cassandraClient.close();
				e.printStackTrace();
			}

		}

	}

	private String getTimestamp(Date date) {
		return sdf.format(date);
	}

	private void recordCassandra(String whoAmI, double avgCPUUtilization, String sTrTimstamp) {
		String[] values = { whoAmI, sTrTimstamp, String.valueOf(avgCPUUtilization) };
		cassandraClient.insertCPU(values);
	}

	private double monitorInstance(AWSCredentials credential, String instanceId) {
		try {
			AmazonCloudWatchClient cw = new AmazonCloudWatchClient(credential);
			Region usWest2 = Region.getRegion(Regions.US_WEST_2);
			cw.setRegion(usWest2);
			GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
					.withStartTime(new Date(new Date().getTime() - offsetInMilliseconds)).withNamespace("AWS/EC2")
					.withPeriod(60).withDimensions(new Dimension().withName("InstanceId").withValue(instanceId))
					.withMetricName("CPUUtilization").withStatistics("Average", "Maximum").withEndTime(new Date());
			GetMetricStatisticsResult getMetricStatisticsResult = cw.getMetricStatistics(request);

			double avgCPUUtilization = 0;
			List<Datapoint> dataPoint = getMetricStatisticsResult.getDatapoints();
			for (Object aDataPoint : dataPoint) {
				Datapoint dp = (Datapoint) aDataPoint;

				avgCPUUtilization = dp.getAverage();
				System.out.println(instanceId + " : " + dp.getTimestamp() + " : " + dp.getAverage());
				recordCassandra(instanceId, avgCPUUtilization, getTimestamp(dp.getTimestamp()));
			}

			return avgCPUUtilization;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		}
		return 0;
	}

	public static String retrieveInstanceId() {
		String EC2Id = "";
		String inputLine;
		URL EC2MetaData;
		try {
			EC2MetaData = new URL("http://169.254.169.254/latest/meta-data/instance-id");
			URLConnection EC2MD = EC2MetaData.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(EC2MD.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				EC2Id = inputLine;
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" Instance ID " + EC2Id);
		return EC2Id;
	}
	
	
	public static int getNumOfWorkerThreads(){
		return 1*10;
	}
	
	public static boolean isTimeLimitReached(){
		//String ec2Id = retrieveInstanceId();
		/**
		 * Code for timeout reached if so return true;
		 */
		return false;
	}
	
}
