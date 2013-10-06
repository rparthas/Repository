package server;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import Utility.QueueDetails;
import Utility.QueueUtility;
import client.Task;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<QueueDetails> queueDetailsList = receiveViaSQS();
		for (QueueDetails qu : queueDetailsList) {
			QueueUtility utility = new QueueUtility(qu.getProps());
			Task task = utility.retrieveMessage(qu.getRequestQueue(),
					"tcp://"+qu.getHostName()+":61616");
			if (task != null) {
				task.execute();
			}
		}
	}

	private static List<QueueDetails> receiveViaSQS() {
		List<QueueDetails> details = new ArrayList<QueueDetails>();
		try {
			AWSCredentials credentials = new BasicAWSCredentials(
					"AKIAISAKBFD5OKP3GJTA",
					"VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");
			AmazonSQSClient sqs = new AmazonSQSClient(credentials);
			Region region = Region.getRegion(Regions.US_EAST_1);
			sqs.setRegion(region);
			CreateQueueRequest createQueueRequest = new CreateQueueRequest(
					"Queue");
			String myQueueUrl = sqs.createQueue(createQueueRequest)
					.getQueueUrl();
			ReceiveMessageRequest res = new ReceiveMessageRequest();
			res.setQueueUrl(myQueueUrl);
			ReceiveMessageResult result = sqs.receiveMessage(res);
			for (Message msg : result.getMessages()) {
				ByteArrayInputStream bis = new ByteArrayInputStream(msg
						.getBody().getBytes());
				ObjectInputStream oInputStream = new ObjectInputStream(bis);
				Object obj = oInputStream.readObject();
				if (obj instanceof QueueDetails) {
					QueueDetails qu = (QueueDetails) obj;
					details.add(qu);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return details;
	}
}
