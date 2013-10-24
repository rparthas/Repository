package queue.hazelcast;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.AtomicNumber;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Hazel_Node extends Thread {
	/**
	 * Will start a new Hazel node or join existing cluster
	 * 
	 * For joining Ec2 nodes make sure that you are executing it from within ec2
	 * nodes.
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		// Config cfg = new XmlConfigBuilder("ec2_hazelcast.xml").build();
		Config cfg = new XmlConfigBuilder("hazelcast.xml").build();
		Hazelcast.newHazelcastInstance(cfg);
		ExecutorService executor = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 10; i++) {
			Hazel_Node objHazel_Node = new Hazel_Node();
			executor.execute(objHazel_Node);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			  e.printStackTrace();
			}
		Hazelcast.shutdownAll();
	}

	public void run() {
		QueueHazelcastUtil utilObj = new QueueHazelcastUtil();
		HazelcastInstance abc = utilObj.getClient();
		AtomicNumber a1 = abc.getAtomicNumber("default");
		for (int i = 0; i < 100; i++)
			{
			System.out.println(Thread.currentThread().getName()+" :-  "+a1.incrementAndGet());
			}
		utilObj.closeClient();

	}
}