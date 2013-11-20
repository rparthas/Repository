package monitor;

import static utility.Constants.HAZEL_NUMWORKERS;
import static utility.Constants.BUSYWORKERCOUNT;
import static utility.Constants.FREEWORKERCOUNT;

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
import com.hazelcast.client.HazelcastClient;
public class WorkerMonitor implements Runnable {

	/**
	 * @param args
	 */
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:z");
	

	final static long offsetInMilliseconds = 1000 * 60 * 2;


	
	SimpleClient cassandraClient;
	public WorkerMonitor() {
		super();
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			String cassServerlist = properties.getProperty("cassServerlist");
			cassandraClient = new SimpleClient();
			cassandraClient.connect(cassServerlist);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Thread(new WorkerMonitor()).start();
	}

	@Override
	public void run() {
		AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider().getCredentials();
		//AWSCredentials credentials = new BasicAWSCredentials("AKIAISAKBFD5OKP3GJTA", "VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");
		String whoAmI;
		whoAmI = retrieveInstanceId();
		// whoAmI = "i-6e9c5f5b";
		while (true) {
			try {
				monitorInstance(credentials, whoAmI);
				Thread.sleep(1000 * 60);
			} catch (Exception e) {
				cassandraClient.close();
				e.printStackTrace();
			}

		}

	}

	public static String getTimestamp(Date date) {
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
				Date time = dp.getTimestamp();
				recordCassandra(instanceId, avgCPUUtilization, getTimestamp(time));
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EC2Id;
	}
	
	
	public static long getNumOfWorkerThreads(HazelcastClient hazelClinetObj){
		return hazelClinetObj.getAtomicNumber(HAZEL_NUMWORKERS).get();
	}
	public static long incrNumOfWorkerThreads(HazelcastClient hazelClinetObj) {
		return hazelClinetObj.getAtomicNumber(HAZEL_NUMWORKERS).incrementAndGet();
	}
	
	public static long incrFreeWorkerCount(HazelcastClient hazelClinetObj) {
		return hazelClinetObj.getAtomicNumber(FREEWORKERCOUNT).incrementAndGet();
	}
	
	public static long incrBusyetAtomicNumber(HazelcastClient hazelClinetObj) {
		return hazelClinetObj.getAtomicNumber(BUSYWORKERCOUNT).incrementAndGet();
	}
	
	public static long decrNumOfWorkerThreads(HazelcastClient hazelClinetObj) {
		return hazelClinetObj.getAtomicNumber(HAZEL_NUMWORKERS).decrementAndGet();
	}
	
	public static boolean isTimeLimitReached(){
		//String ec2Id = retrieveInstanceId();
		/**
		 * Code for timeout reached if so return true;
		 */
		return false;
	}
}
