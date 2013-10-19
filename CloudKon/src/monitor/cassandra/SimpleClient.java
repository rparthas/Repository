package monitor.cassandra;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Query;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class SimpleClient {

	private Cluster cluster;
	private Session session;
	final String[] columns = { "sensor_id", "collected_at", "util_percent" };

	public void connect(String node) {
		cluster = Cluster.builder().withPort(9042).addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}
		session = cluster.connect();
	}

	public void close() {
		cluster.shutdown();
	}

	public void insert(String[] values) {
		Query query = QueryBuilder.insertInto("cs554_cloudkon", "cpuUtil").values(columns, values);
		session.executeAsync(query);
	}

	
}