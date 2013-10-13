package queue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;

import utility.QueueDetails;

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


public class SQS implements DistributedQueue {

	AWSCredentials credentials = new BasicAWSCredentials(
			"AKIAISAKBFD5OKP3GJTA", "VfhFqZTqMqNLatuRY+r86SZlwRmJOUCq2WYxVPPR");
	
	public static final String DQ ="Queue";

	public void pushToQueue(ByteArrayOutputStream bos) {
		// TODO Auto-generated method stub
		AmazonSQSClient sqs = new AmazonSQSClient(credentials);
		Region region = Region.getRegion(Regions.US_EAST_1);
		sqs.setRegion(region);
		CreateQueueRequest createQueueRequest = new CreateQueueRequest(DQ);
		String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
		SendMessageRequest req = new SendMessageRequest();
		req.setQueueUrl(myQueueUrl);
		req.setMessageBody(bos.toString());
		sqs.sendMessage(req);
	}

	@Override
	public QueueDetails pullFromQueue() {
		// TODO Auto-generated method stub
		QueueDetails details = null;
		try {
			AmazonSQSClient sqs = new AmazonSQSClient(credentials);
			Region region = Region.getRegion(Regions.US_EAST_1);
			sqs.setRegion(region);
			CreateQueueRequest createQueueRequest = new CreateQueueRequest(DQ	);
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
					details = (QueueDetails) obj;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return details;
	}

}
