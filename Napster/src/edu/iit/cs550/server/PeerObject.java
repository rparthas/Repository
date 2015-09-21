package edu.iit.cs550.server;

import java.io.Serializable;

public class PeerObject implements Serializable {


	@Override
	public String toString() {
		return "PeerObject [ipAddress=" + ipAddress + ", directory="
				+ directory + ", port=" + port + "]";
	}

	private static final long serialVersionUID = 1007483499861765794L;

	private String ipAddress;
	
	private String directory;
	
	private int port;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
}
