package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import queue.hazelcast.Hazel_Node;

public class CloudKonStartUp {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException, InterruptedException {
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			
			
			Properties properties = new Properties();
			properties.load(reader);
			if(properties.getProperty("numWorkers").equals("true"));{
				Hazel_Node.main(args);	
			}
			
			int numClients = Integer.parseInt(properties
					.getProperty("numClients"));
			int numWorkers = Integer.parseInt(properties
					.getProperty("numWorkers"));
			// Starting workers
			for (int i = 0; i < numWorkers; i++) {
				new WorkerStarter().start();
			}

			// Starting clients
			for (int i = 0; i < numClients; i++) {
				new ClientStarter().start();
			}
		}

	}

}
