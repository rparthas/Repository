package ram.edu.twitterStorm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

import java.util.HashMap;

/**
 * Main Class for Creating topology and running
 */
public class HashToplogy {
	public static void main(String[] args) {

		LocalCluster cluster = new LocalCluster();

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("tweetSpout", new TweetSpout(), 1);
		builder.setBolt("reportBolt", new ReportBolt(), 10).globalGrouping("tweetSpout");
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(2);
		cluster.submitTopology("hashTopology", conf, builder.createTopology());
		Utils.sleep(300000);
		cluster.killTopology("hashTopology");
		cluster.shutdown();
	}
}
