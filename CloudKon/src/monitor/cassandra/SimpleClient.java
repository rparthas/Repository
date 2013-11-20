package monitor.cassandra;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import au.com.bytecode.opencsv.CSVWriter;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Query;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.sun.mail.iap.ByteArray;

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
		for (String asset : splits) {
			cBuilder.addContactPoint(asset);
		}
		cluster = cBuilder.build();
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

	public void getQStatus(String clientName) throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		String client_id, collected_at, queueLength;
		Clause mywhere = QueryBuilder.eq("client_id", clientName);
		Query query = QueryBuilder.select().all()
				.from("cs554_cloudkon", "queuestatus").where(mywhere);
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

	public void getClientStatus(ConcurrentMap<String, String> mapClientStatus)
			throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		/*
		 * String client_id, collected_at, status; Query query =
		 * QueryBuilder.select().all() .from("cs554_cloudkon", "clientstatus");
		 * ResultSetFuture results = session.executeAsync(query); for (Row row :
		 * results.getUninterruptibly()) { client_id =
		 * row.getString("client_id"); collected_at =
		 * row.getString("collected_at"); status = row.getString("status");
		 * System.out.printf("%s: %s / %s\n", client_id, collected_at, status);
		 * data.add(new String[] { client_id, collected_at, status }); }
		 */
		Collection<String> keySet = mapClientStatus.keySet();
		int counter = 0;
		for (String key : keySet) {
			String split[] = key.split(",");
			data.add(new String[] { split[0], mapClientStatus.get(key),
					split[1], "hazel" });
			System.out.printf("%s: %s / %s\n", split[0],
					mapClientStatus.get(key), split[1]);
			counter++;
		}
		System.out.println("Total count " + counter);
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
		session.execute("DROP KEYSPACE IF EXISTS cs554_cloudkon ; ");
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
			csv = inpFilename + ".csv";
		}
		CSVWriter writer = new CSVWriter(new FileWriter(csv));
		writer.writeAll(data);
		System.out.println("CSV written successfully.");
		writer.close();
	}

	public void insertData(ByteArray byteArr) {
		// TODO Auto-generated method stub

	}

	public void getWorkerCountStatus(
			ConcurrentMap<String, Long> mapWorkerCountStatus) throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		Collection<String> keySet = mapWorkerCountStatus.keySet();
		for (String key : keySet) {
			String val =String.valueOf(mapWorkerCountStatus.get(key));
			data.add(new String[] { key, val });
			System.out.printf("%s , %s\n", key, val);
		}
		writeCsvfile(data);
		
	}
	
	public void getThroughPutStatus(ConcurrentMap<String, String> mapStatus)
			throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		Collection<String> keySet = mapStatus.keySet();
		for (String key : keySet) {
			String val =mapStatus.get(key);
			data.add(new String[] { key, val });
			System.out.printf("%s , %s\n", key, val);
		} 
		writeCsvfile(data);

	}

	public void getWorkerStatus(ConcurrentMap<String, String> mapStatus) throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		Collection<String> keySet = mapStatus.keySet();
		for (String key : keySet) {
			String val =mapStatus.get(key);
			String split[] = val.split(",");
			data.add(new String[] { key, split[0],split[1] });
			System.out.printf("%s , %s , %s \n", key, split[0],split[1]);
		} 
		writeCsvfile(data);
		
	}

	public void getQStatus(ConcurrentMap<String, String> mapQLengthStatus) throws IOException {
		List<String[]> data = new ArrayList<String[]>();
		Collection<String> keySet = mapQLengthStatus.keySet();
		for (String key : keySet) {
			String val =mapQLengthStatus.get(key);
			data.add(new String[] { key, val });
			System.out.printf("%s , %s  \n", key, val);
		} 
		writeCsvfile(data);
		
	}


}