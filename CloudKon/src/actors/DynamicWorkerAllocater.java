package actors;

import static utility.Constants.MASTER_QUEUE_LENGTH;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import monitor.WorkerMonitor;
import queue.hazelcast.Hazel_Node;
import queue.hazelcast.QueueHazelcastUtil;
import utility.PrintManager;
import utility.WorkerStarter;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.RequestSpotInstancesRequest;
import com.amazonaws.services.ec2.model.RequestSpotInstancesResult;
import com.hazelcast.client.HazelcastClient;

public class DynamicWorkerAllocater {

	public static void main(String[] args) throws InterruptedException,
			IOException {
		FileReader reader = new FileReader("CloudKon.properties");
		Properties properties = new Properties();
		properties.load(reader);
		long workercountlimit = Long.parseLong(properties
				.getProperty("workercountlimit"));
		AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider()
				.getCredentials();
		AmazonEC2 ec2 = new AmazonEC2Client(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(usWest2);
		long loopcount = 0;
		long prevWorkerCount = 9999;
		long requestedWorkers = 0;
		QueueHazelcastUtil objQueueHazelcastUtil = new QueueHazelcastUtil();
		HazelcastClient hazelClinetObj = objQueueHazelcastUtil.getClient();
		String spotPrice = properties.getProperty("spotPrice");
		String AmiID = properties.getProperty("AmiID");
		String instanceType = properties.getProperty("instanceType");
		String secGroup = properties.getProperty("secGroup");
		while (true) {
			long currWorkerCount = WorkerMonitor
					.getNumOfWorkerThreads(hazelClinetObj);
			long currMasterQLen = hazelClinetObj.getAtomicNumber(
					MASTER_QUEUE_LENGTH).get();
			PrintManager.PrintProdMessage("currWorkerCount " + currWorkerCount
					+ "  currMasterQLen " + currMasterQLen);
			long requiredWorkers = currMasterQLen;
			// Requested Workers are allocated than last run
			if (currWorkerCount > prevWorkerCount) {
				PrintManager
						.PrintProdMessage("Worker AMI's Started After request was made "
								+ System.nanoTime());
				PrintManager.PrintProdMessage("adjusting Requested worker"
						+ requestedWorkers + " diff "
						+ (currWorkerCount - prevWorkerCount));
				requestedWorkers = requestedWorkers
						- (currWorkerCount - prevWorkerCount);
				PrintManager.PrintProdMessage("adjusted Requested worker "
						+ requestedWorkers);
			}
			// To avoid re-requesting for same advertisements
			requiredWorkers = requiredWorkers - requestedWorkers;
			PrintManager.PrintProdMessage("requiredWorkers " + requiredWorkers);
			if (currWorkerCount < workercountlimit) {
				if (requiredWorkers + currWorkerCount > workercountlimit) {
					loopcount = workercountlimit - currWorkerCount;
					PrintManager.PrintProdMessage("Limit check " + loopcount);
				} else {
					loopcount = requiredWorkers;
				}
				PrintManager.PrintProdMessage("Requesting " + loopcount
						+ "Worker AMI at" + System.nanoTime());
				requestWorker(spotPrice, AmiID, instanceType, secGroup, ec2,
						(int) loopcount);
				requestedWorkers += loopcount;
				for (int i = 0; i < loopcount; i++) {
					PrintManager.PrintProdMessage("Requesting Worker AMI Start"
							+ System.nanoTime());
					requestedWorkers++;
					// new WorkerStarter(worker++).start();
				}
			}
			prevWorkerCount = currWorkerCount;
			PrintManager.PrintProdMessage("Requested worker "
					+ requestedWorkers);
			Thread.sleep(2000);
		}

	}

	public static void requestWorker(String spotPrice, String AmiID,
			String instanceType, String secGroup, AmazonEC2 ec2,
			int numinstances) {
		RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();
		requestRequest.setSpotPrice(spotPrice);
		requestRequest.setInstanceCount(Integer.valueOf(numinstances));
		LaunchSpecification launchSpecification = new LaunchSpecification();
		launchSpecification.setImageId(AmiID);
		launchSpecification.setInstanceType(instanceType);
		ArrayList<String> securityGroups = new ArrayList<String>();
		securityGroups.add(secGroup);
		launchSpecification.setSecurityGroups(securityGroups);
		requestRequest.setLaunchSpecification(launchSpecification);
		ec2.requestSpotInstances(requestRequest);

	}

}
