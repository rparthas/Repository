package edu.iit.cs550.server;

import java.util.Properties;

import edu.iit.cs550.utility.Constants;
import edu.iit.cs550.utility.UtilityClass;

/**
 * Starting Point for Indexing Server
 * 
 * @author Rajagopal
 *
 */
public class StartServer {

	/**
	 * Main Method for starting the indexing server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Properties prop = UtilityClass.loadProperties();
		int port = Integer.parseInt(prop.getProperty(Constants.SERVERPORT));
		int serverConnections = Integer.parseInt(prop
				.getProperty(Constants.SERVERTHREADS));
		try {
			new Server(port, serverConnections).connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
