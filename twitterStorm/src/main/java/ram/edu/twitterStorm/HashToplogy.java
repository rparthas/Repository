package ram.edu.twitterStorm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

/**
 * Main Class for Creating topology and running
 */
public class HashToplogy {
	public static void main(String[] args) {

		LocalCluster cluster = new LocalCluster();

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("tweetSpout", new TweetSpout(), 1);
		builder.setBolt("parseTweetBolt", new ParseTweetBolt(), 10).shuffleGrouping("tweetSpout");
		builder.setBolt("rollingCountBolt", new RollingCountBolt(500), 15).fieldsGrouping("parseTweetBolt",
				new Fields("hashTag"));
		builder.setBolt("reportBolt", new ReportBolt(), 10).globalGrouping("rollingCountBolt");
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(10);
		cluster.submitTopology("hashTopology", conf, builder.createTopology());
		Utils.sleep(300000);
		cluster.killTopology("hashTopology");
		cluster.shutdown();
	}
}
