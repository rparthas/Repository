package edu.iit.cs550.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Calendar;
import java.util.Properties;

import edu.iit.cs550.server.TransferObject;

public class UtilityClass {

	public static Properties loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(Constants.FILENAME);
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
		return prop;
	}

	public static String getMyIP() throws Exception {
		String ip = Inet4Address.getLocalHost().getHostAddress().toString();
		return ip;
	}

	/**
	 * Writes an output object back to the requestor
	 * 
	 * @param object
	 * @throws Exception
	 */
	public static void writeObject(Object object, Socket socket)
			throws Exception {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(object);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the input stream and converts it into transfer object
	 * 
	 * @param socket
	 * @return
	 * @throws Exception
	 */
	public static TransferObject readObject(Socket socket) throws Exception {
		TransferObject to = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			Object obj = ois.readObject();
			if (obj instanceof TransferObject) {
				to = (TransferObject) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return to;
	}
	
	public static long getTime(){
		return Calendar.getInstance().getTimeInMillis();
	}
}
