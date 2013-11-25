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
			if(properties.getProperty("nodePartHazel").equals("true")){
				Hazel_Node.main(args);	
			}
			int numClients = Integer.parseInt(properties
					.getProperty("numClients"));
			int numWorkers = Integer.parseInt(properties
					.getProperty("numWorkers"));
			String resourceAllocationMode= properties.getProperty("resourceAllocationMode");
			PrintManager.PrintMessage(" >> resouceAllocationMode <<  "+resourceAllocationMode);
			if (resourceAllocationMode.equalsIgnoreCase("static")){
				// Starting workers
				for (int i = 0; i < numWorkers; i++) {
					new WorkerStarter(i).start();
				}
				// Starting clients
				for (int i = 0; i < numClients; i++) {
					new ClientStarter().start();
				}

			}else{
				for (int i = 0; i < numClients; i++) {
					new StagedClientStarter().start();
				}
				// Starting workers
				for (int i = 0; i < numWorkers; i++) {
					new WorkerStarter(i).start();
				}
			}
			//starting CPU monitor for workers
			if(properties.getProperty("monitoringEnabled").equals("true")&&numWorkers>0){
				//new Thread(new WorkerMonitor()).start();
			}
		}

	}

}
