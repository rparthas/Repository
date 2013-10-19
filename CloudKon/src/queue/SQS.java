package queue;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import entity.QueueDetails;

public class SQS implements DistributedQueue {

	AWSCredentials credentials = new BasicAWSCredentials(
			"AKIAISAKBFD5OKP3GJTA", "VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");

	public static final String MASTER = "Master";

	public void pushToQueue(QueueDetails details) {
		// TODO Auto-generated method stub
		try {
			AmazonSQSClient sqs = new AmazonSQSClient(credentials);
			Region region = Region.getRegion(Regions.US_EAST_1);
			sqs.setRegion(region);
			CreateQueueRequest createQueueRequest = new CreateQueueRequest(
					MASTER);
			String myQueueUrl = sqs.createQueue(createQueueRequest)
					.getQueueUrl();
			SendMessageRequest req = new SendMessageRequest();
			req.setQueueUrl(myQueueUrl);
			String msg = details.toString();
			req.setMessageBody(msg);
			sqs.sendMessage(req);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public QueueDetails pullFromQueue() {
		// TODO Auto-generated method stub
		QueueDetails details = null;
		try {
			AmazonSQSClient sqs = new AmazonSQSClient(credentials);
			Region region = Region.getRegion(Regions.US_EAST_1);
			sqs.setRegion(region);
			CreateQueueRequest createQueueRequest = new CreateQueueRequest(
					MASTER);
			String myQueueUrl = sqs.createQueue(createQueueRequest)
					.getQueueUrl();
			ReceiveMessageRequest res = new ReceiveMessageRequest();
			res.setQueueUrl(myQueueUrl);
			ReceiveMessageResult result = sqs.receiveMessage(res);
			for (Message msg : result.getMessages()) {
				String message = msg.getBody();
				if (message != null) {
					details = new QueueDetails(message);
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return details;
	}

}
