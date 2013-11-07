package queue;


import utility.PrintManager;

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
import static utility.Constants.MASTER;
import entity.QueueDetails;

public class SQS implements DistributedQueue {

	AWSCredentials credentials = new BasicAWSCredentials(
			"AKIAISAKBFD5OKP3GJTA", "VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");

	

	public void pushToQueue(QueueDetails details) {
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
			PrintManager.PrintException(e);
		}

	}

	@Override
	public QueueDetails pullFromQueue() {
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
			PrintManager.PrintException(e);
		}
		return details;
	}

}
