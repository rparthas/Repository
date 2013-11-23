package actors;

import static utility.Constants.MASTER_QUEUE_LENGTH;

import java.io.IOException;

import monitor.WorkerMonitor;
import queue.hazelcast.Hazel_Node;
import queue.hazelcast.QueueHazelcastUtil;
import utility.PrintManager;
import utility.WorkerStarter;

import com.hazelcast.client.HazelcastClient;

public class DynamicWorkerAllocater {

	public static void main(String[] args) throws InterruptedException,
			IOException {
		Hazel_Node.main(args);
		int worker = 0;
		long limit = 4;
		long loopcount=0;
		QueueHazelcastUtil objQueueHazelcastUtil = new QueueHazelcastUtil();
		HazelcastClient hazelClinetObj = objQueueHazelcastUtil.getClient();

		while (true) {
			long currWorkerCount = WorkerMonitor
					.getNumOfWorkerThreads(hazelClinetObj);
			long currMasterQLen = hazelClinetObj.getAtomicNumber(
					MASTER_QUEUE_LENGTH).get();
			PrintManager.PrintProdMessage("currWorkerCount " + currWorkerCount);
			PrintManager.PrintProdMessage("currMasterQLen " + currMasterQLen);
			if (currWorkerCount < limit) {
				if (currMasterQLen + currWorkerCount > limit){
					loopcount  = limit;
				}else{
					loopcount=currMasterQLen;
				}
				for (int i = 0; i < loopcount; i++) {
					PrintManager.PrintProdMessage("Starting worker ami!!!!!");
					new WorkerStarter(worker++).start();
				}
			}
			Thread.sleep(2000);
		}

	}

}
