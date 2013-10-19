package queue.hazelcast;

import java.io.File;
import java.io.IOException;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;

public class Hazel_Node {
/**
 * Will start a new Hazel node or join existing cluster
 * 
 * For joining Ec2 nodes make sure that you are executing it from within ec2 nodes. 
 * 
 * @param args
 * @throws InterruptedException
 * @throws IOException
 */
	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println(new File(".").getCanonicalPath());
		Config cfg = new XmlConfigBuilder("hazelcast.xml").build();
		//Config cfg = new XmlConfigBuilder("ec2_hazelcast.xml").build();
		 Hazelcast.newHazelcastInstance(cfg);
	}
}