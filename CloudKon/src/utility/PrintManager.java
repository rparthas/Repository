package utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PrintManager {
	static String mode = null;

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
			PrintManager.PrintMessage(Message);
		}
	}
}
