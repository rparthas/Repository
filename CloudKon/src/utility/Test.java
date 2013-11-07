package utility;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.hazelcast.client.ClientConfig;

public class Test {

	public static void main(String args[]) {
		
		try (FileReader reader = new FileReader("CloudKon.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			String serverLoc = properties.getProperty("hazelCastServerList");
			String[] splits = serverLoc.split(",");
			PrintManager.PrintMessage("splits.size: " + splits.length);
			for (String asset : splits) {
				PrintManager.PrintMessage("address " +asset);
			}
		} catch (IOException ex) {
			PrintManager.PrintException(ex);
		}

		/*try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				"tasks.txt"))) {
			for (int i = 0; i < 100; i++) {
				writer.append(1000 + "");
				writer.newLine();
			}
		} catch (Exception e) {
				PrintManager.PrintException(e);
		} finally {

		}*/

	}
}
