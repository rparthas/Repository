package queue.hazelcast;

import static utility.Constants.HAZEL_NUMWORKERS;

import java.io.IOException;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Hazel_Node {
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
		Config cfg = new XmlConfigBuilder("hazelcast.xml").build();
		Hazelcast.newHazelcastInstance(cfg);
		/*QueueHazelcastUtil utilObj = new QueueHazelcastUtil();
		HazelcastInstance hazelClinetObj = utilObj.getClient();
		hazelClinetObj.getAtomicNumber(HAZEL_NUMWORKERS).set(0);*/
	}

}