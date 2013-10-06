package client;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Utility.QueueDetails;
import Utility.QueueUtility;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class Client {

	public static void main(String args[]) throws Exception {

		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		props.setProperty("queue.queue1", "queue1");
		props.setProperty("queue.queue2", "queue2");
		QueueDetails qu = new QueueDetails();
		String hName = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 })
				.getHostName();

		qu.setHostName(hName);
		qu.setClientName("client1");
		qu.setRequestQueue("queue1");
		qu.setResponseQueue("queue2");
		qu.setProps(props);

		QueueUtility utility = new QueueUtility(props);
		List<Task> objects = new ArrayList<>();
		objects.add(new Task());
		utility.postMessage(objects, "queue1", "tcp://localhost:61616");
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(bos);
	    os.writeObject(qu);
	   os.close();
		sendViaSQS(bos);
	
		

	}

	private static void sendViaSQS(ByteArrayOutputStream bos) {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAISAKBFD5OKP3GJTA", "VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");
		AmazonSQSClient sqs = new AmazonSQSClient(credentials);
		Region region = Region.getRegion(Regions.US_EAST_1);
		sqs.setRegion(region);
		CreateQueueRequest createQueueRequest = new CreateQueueRequest(
				"Queue");
		String myQueueUrl = sqs.createQueue(createQueueRequest)
				.getQueueUrl();
		SendMessageRequest req = new SendMessageRequest();
		req.setQueueUrl(myQueueUrl);
		req.setMessageBody(bos.toString());
		sqs.sendMessage(req);
	}
}
