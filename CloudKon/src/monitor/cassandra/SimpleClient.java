package monitor.cassandra;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVWriter;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Query;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class SimpleClient {

	private Cluster cluster;
	private Session session;
	final String[] columnsCPU = { "instance_id", "collected_at", "util_percent" };
	final String[] columnsnodestatus = { "instance_id", "collected_at",
			"nStatus" };
	final String[] columnsQueuetatus = { "client_id", "collected_at",
			"queueLength" };
	final String[] columnsClientStatus = { "client_id", "collected_at",
			"status" };

	public void connect(String node) {

		Builder cBuilder = Cluster.builder().withPort(9042);
		String[] splits = node.split(":");

		System.out.println("splits.size: " + splits.length);

		for (String asset : splits) {
			cBuilder.addContactPoint(asset);
		}

		cluster = cBuilder.build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect();
	}

	public void close() {
		cluster.shutdown();
	}

	public void insertCPU(String[] values) {
		Query query = QueryBuilder.insertInto("cs554_cloudkon", "cpuUtil")
				.values(columnsCPU, values);
		session.execute(query);
	}

	public void insertNodeStatus(String[] values) {
		Query query = QueryBuilder.insertInto("cs554_cloudkon", "nodestatus")
				.values(columnsnodestatus, values);
		session.execute(query);
	}

	public void insertQlength(String[] values) {
		Query query = QueryBuilder.insertInto("cs554_cloudkon", "queuestatus")
				.values(columnsQueuetatus, values);
		session.execute(query);
	}

	public void insertClientStatus(String[] values) {
		Query query = QueryBuilder.insertInto("cs554_cloudkon", "clientstatus")
				.values(columnsClientStatus, values);
		session.execute(query);
	}

	public void getRowsStatus() {
		Query query = QueryBuilder.select().all()
				.from("cs554_cloudkon", "nodestatus");
		ResultSetFuture results = session.executeAsync(query);
		int counter = 0;
		for (Row row : results.getUninterruptibly()) {
			System.out.printf("%s: %s / %s\n", row.getString("instance_id"),
					row.getString("collected_at"), row.getString("nStatus"));
			counter++;
		}
		System.out.println("counter " + counter);
	}

	public void getQStatus() throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		String client_id, collected_at, queueLength;
		Query query = QueryBuilder.select().all()
				.from("cs554_cloudkon", "queuestatus");
		ResultSetFuture results = session.executeAsync(query);
		for (Row row : results.getUninterruptibly()) {
			client_id = row.getString("client_id");
			collected_at = row.getString("collected_at");
			queueLength = row.getString("queueLength");
			System.out
					.printf("%s: %s / %s\n", row.getString("client_id"),
							row.getString("collected_at"),
							row.getString("queueLength"));
			data.add(new String[] { client_id, collected_at, queueLength });
		}
		writeCsvfile(data);
	}

	public void getClientStatus() throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		String client_id, collected_at, status;
		Query query = QueryBuilder.select().all()
				.from("cs554_cloudkon", "clientstatus");
		ResultSetFuture results = session.executeAsync(query);
		for (Row row : results.getUninterruptibly()) {
			client_id = row.getString("client_id");
			collected_at = row.getString("collected_at");
			status = row.getString("status");
			System.out.printf("%s: %s / %s\n", row.getString("client_id"),
					row.getString("collected_at"), row.getString("status"));
			data.add(new String[] { client_id, collected_at, status });
		}
		writeCsvfile(data);
	}

	public void getRowsCpu() throws IOException {
		String instanceid, collected_at, util_percent;
		Query query = QueryBuilder.select().all()
				.from("cs554_cloudkon", "cpuUtil");
		ResultSetFuture results = session.executeAsync(query);
		List<String[]> data = new ArrayList<String[]>();

		for (Row row : results.getUninterruptibly()) {
			instanceid = row.getString("instance_id");
			collected_at = row.getString("collected_at");
			util_percent = row.getString("util_percent");

			data.add(new String[] { instanceid, collected_at, util_percent });
			System.out.printf("%s: %s / %s\n", instanceid, collected_at,
					util_percent);
		}
		writeCsvfile(data);
	}

	public void createSchema() {
		session.execute("DROP KEYSPACE cs554_cloudkon ; ");
		session.execute("CREATE KEYSPACE cs554_cloudkon WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':2};");
		session.execute("CREATE TABLE cs554_cloudkon.nodestatus ("
				+ "instance_id text," + "collected_at text," + "nStatus text,"
				+ "PRIMARY KEY (instance_id, collected_at)"
				+ ") WITH COMPACT STORAGE");

		session.execute("CREATE TABLE cs554_cloudkon.cpuUtil ("
				+ "instance_id text," + "collected_at text,"
				+ "util_percent text,"
				+ "PRIMARY KEY (instance_id, collected_at)"
				+ ")WITH COMPACT STORAGE");

		session.execute("CREATE TABLE cs554_cloudkon.queuestatus ("
				+ "client_id text," + "collected_at text,"
				+ "queueLength text," + "PRIMARY KEY (client_id, collected_at)"
				+ ")WITH COMPACT STORAGE");
		session.execute("CREATE TABLE cs554_cloudkon.clientstatus ("
				+ "client_id text," + "collected_at text," + "status text,"
				+ "PRIMARY KEY (client_id, collected_at)" + ")");
		session.execute("CREATE INDEX ind_status on cs554_cloudkon.clientstatus(status) ;");
	}

	void writeCsvfile(List<String[]> data) throws IOException {
		String csv = "./output.csv";
		Scanner readinp = new Scanner(System.in);
		System.out.println("Enter file name");
		String inpFilename = readinp.nextLine();
		if (inpFilename.length() >= 0) {
			csv = inpFilename+".csv";
		}
		CSVWriter writer = new CSVWriter(new FileWriter(csv));
		writer.writeAll(data);
		System.out.println("CSV written successfully.");
		writer.close();
	}

}