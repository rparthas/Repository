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
				PrintManager.PrintException(e);
			}
		}else if (mode.equals("development")) {
			System.out.println(Message);
			log.debug(Message);
		}else if (mode.equals("logOnly")) {
			log.debug(Message);
		}
	}
	public static void PrintProdMessage(String Message) {
		if (mode == null) {
			try {
				FileReader reader = new FileReader("CloudKon.properties");
				Properties properties = new Properties();
				properties.load(reader);
				mode = properties.getProperty("printMode");
			} catch (IOException e) {
				PrintManager.PrintException(e);
			}
		}else if (mode.equals("development")) {
			System.out.println(Message);
			log.debug(Message);
		}else if (mode.equals("logOnly")) {
			log.debug(Message);
		}else if (mode.equals("production")) {
			System.out.println(Message);
			log.debug(Message);
		}
	}
	public static void PrintException(Exception exep) {
		if (mode == null) {
			try {
				FileReader reader = new FileReader("CloudKon.properties");
				Properties properties = new Properties();
				properties.load(reader);
				mode = properties.getProperty("printMode");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (mode.equals("development")) {
			exep.printStackTrace();
			log.fatal("EXCEPTION", exep);
		}else if (mode.equals("logOnly")) {
			log.fatal("EXCEPTION", exep);
		}else if (mode.equals("production")) {
			log.fatal("EXCEPTION",exep);
		}
	}
}
