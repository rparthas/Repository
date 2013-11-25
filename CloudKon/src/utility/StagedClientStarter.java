package utility;

import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.Semaphore;

import actors.StagedClient;

public class StagedClientStarter extends Thread {

	public void run() {
		try {
			FileReader reader = new FileReader("CloudKon.properties");
			Properties properties = new Properties();
			properties.load(reader);
			String StageFileName = properties.getProperty("StageFileName");
			int numberofStages = Integer.parseInt(properties.getProperty("numberofStages"));
			final Semaphore stageLock = new Semaphore(1);
			String strFileName;
			for (int i = 1; i <= numberofStages; i++) {
				PrintManager.PrintMessage("Stage-"+i+ " STARTED "+System.nanoTime());
				strFileName = StageFileName + i + ".txt";
				StagedClient.main(stageLock, strFileName);
				PrintManager
						.PrintMessage(" Back in starter >> trying for lock <<");
				stageLock.acquire();
				PrintManager.PrintMessage(" Stage-"+ i+" FINISHED "+System.nanoTime());
				stageLock.release();
			}
			PrintManager.PrintMessage("All Stages FINISHED at time "+System.nanoTime());
			stageLock.release();
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}
	}
}
