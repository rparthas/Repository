package edu.iit.cs550.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.util.Properties;

import edu.iit.cs550.core.PeerObject;

public class UtilityClass {

	public static Properties prop = new Properties();

	static {
		loadProperties();
	}

	private static void loadProperties() {
		InputStream input = null;
		try {
			input = new FileInputStream("E:\\Workspace\\DHT\\src\\config.properties");
			//input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getValue(String key) {
		return prop.getProperty(key);
	}

	public static String getMyIP()  {
		try{
			String ip = Inet4Address.getLocalHost().getHostAddress().toString();
			return ip;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	public static PeerObject getPeer(String peerId) {
		String address = UtilityClass.getValue(peerId);
		String ip = address.split(":")[0];
		int port = Integer.parseInt(address.split(":")[1]);
		return new PeerObject(ip, port);
	}

	public static int getNoOfPeers() {
		return Integer.parseInt(getValue("peers"));
	}

}
