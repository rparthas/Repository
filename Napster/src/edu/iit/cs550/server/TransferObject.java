package edu.iit.cs550.server;

import java.io.Serializable;
import java.util.List;

public class TransferObject implements Serializable {

	private static final long serialVersionUID = 5872778768159963837L;

	private String ipAddress;

	private List<String> files;

	private String directory;

	private int port;

	private String requestFileName;

	private boolean requestFile;

	private List<PeerObject> peers;

	public List<PeerObject> getPeers() {
		return peers;
	}

	public void setPeers(List<PeerObject> peers) {
		this.peers = peers;
	}

	@Override
	public String toString() {
		return "TransferObject [ipAddress=" + ipAddress + ", files=" + files
				+ ", directory=" + directory + ", port=" + port
				+ ", requestFileName=" + requestFileName + ", requestFile="
				+ requestFile + ", peers=" + peers + "]";
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRequestFileName() {
		return requestFileName;
	}

	public void setRequestFileName(String requestFileName) {
		this.requestFileName = requestFileName;
	}

	public boolean isRequestFile() {
		return requestFile;
	}

	public void setRequestFile(boolean requestFile) {
		this.requestFile = requestFile;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
