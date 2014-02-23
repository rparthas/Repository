package logging;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Example {

	private static Logger log = Logger.getLogger(Example.class);
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("D://Projects//windows.space//Simple.Java//src//logging.properties");
		log.info("This is a test");
		Thread.sleep(300);
		log.info("This is a test1");
	}

}
