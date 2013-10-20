package queue.hazelcast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

		ExecutorService executor = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 10; i++) {
			Hazel_Node objHazel_Node = new Hazel_Node();
			executor.execute(objHazel_Node);
		}
	}

	public void run() {
		try {
			Config cfg = new XmlConfigBuilder("hazelcast.xml").build();
			HazelcastInstance h1 = Hazelcast.newHazelcastInstance(cfg);
			AtomicNumber a1 = h1.getAtomicNumber("default");
			for (int i = 0; i < 100; i++)
				System.out.println(a1.incrementAndGet());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}