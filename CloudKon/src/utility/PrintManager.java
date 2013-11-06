package utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PrintManager {
	static String mode = null;
	static Logger log = Logger.getLogger(
			PrintManager.class.getName());
	public static void PrintMessage(String Message) {
		if (mode == null) {
			try {
				FileReader reader = new FileReader("CloudKon.properties");
				Properties properties = new Properties();
				properties.load(reader);
				mode = properties.getProperty("printMode");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (mode.equals("development")) {
			System.out.println(Message);
			log.debug(Message);
		}
	}
}
