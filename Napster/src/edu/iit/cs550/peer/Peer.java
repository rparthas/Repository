package edu.iit.cs550.peer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.iit.cs550.server.PeerObject;
import edu.iit.cs550.server.TransferObject;
import edu.iit.cs550.utility.UtilityClass;

/**
 * This is the main class for peer
 * 
 * @author Raj
 *
 */
public class Peer implements Callable<Object> {

	int port = 0;

	String directory = null;

	Socket clientSocket = null;

	String serverAddress = null;

	int serverPort = 0;

	int threadCount = 0;

	private Socket socket = null;// Create a socket for the server

	public Peer(int port, String directory, int serverPort, String ipAddress,
			int threadCount) throws Exception {
		this.port = port;
		this.directory = directory;
		this.serverPort = serverPort;
		this.serverAddress = ipAddress;
		this.threadCount = threadCount;
		openSocket();
	}

	/**
	 * This scans the directory for files and sends the files to server
	 * 
	 * @param serverPort
	 * @param ipAddress
	 * @throws Exception
	 */
	public void register() throws Exception {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					clientSocket.getOutputStream());
			File folder = new File(directory);
			File[] listOfFiles = folder.listFiles();
			List<String> files = new ArrayList<String>();
			TransferObject to = new TransferObject();
			to.setDirectory(directory);
			to.setIpAddress(UtilityClass.getMyIP());
			to.setPort(port);
			to.setRequestFile(false);
			to.setFiles(files);
			if (listOfFiles != null) {
				for (File file : listOfFiles) {
					if (file.isFile()) {
						files.add(file.getName());
					}
				}
			}
			oos.writeObject(to);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * THis is the method to open a socket to start accepting connections
	 */
	public void connect() {

		ExecutorService executorService = Executors
				.newFixedThreadPool(threadCount);
		try (ServerSocket serverSocket = new ServerSocket(port, threadCount);) {

			while (true) {
				socket = serverSocket.accept();
				executorService.submit(this);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * This method is to shutdown the peer
	 * 
	 * @throws Exception
	 */
	public void shutDown() {
		try {
			if (clientSocket != null) {
				clientSocket.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * THis method is to open a socket if it was closed already or open it for
	 * the first time
	 * 
	 * @throws Exception
	 */
	public void openSocket() throws Exception {
		if (clientSocket == null || !clientSocket.isConnected()
				|| !clientSocket.isClosed()) {
			clientSocket = new Socket(InetAddress.getByName(serverAddress),
					serverPort);
		}
	}

	/**
	 * This method is to send the requested file by another peer. It recognizes
	 * if file is requested then just buffers the file and sends it
	 */
	@Override
	public Object call() throws Exception {
		try {
			DataOutputStream dos = new DataOutputStream(
					socket.getOutputStream());
			TransferObject to = UtilityClass.readObject(socket);
			if (to != null) {
				if (to.isRequestFile()) {
					File file = new File(directory + "/"
							+ to.getRequestFileName());
					dos.writeUTF(file.getName());
					Files.copy(file.toPath(), dos);
				}
			}
			dos.flush();
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is used to lookup the file and send the file
	 * 
	 * @throws Exception
	 */
	public List<PeerObject> lookUpFile(String fileName) throws Exception {
		openSocket();
		TransferObject to = new TransferObject();
		to.setRequestFile(true);
		to.setRequestFileName(fileName);
		UtilityClass.writeObject(to, clientSocket);
		to = UtilityClass.readObject(clientSocket);
		List<PeerObject> peers = new ArrayList<PeerObject>();
		if (to != null) {
			peers = to.getPeers();
		}
		return peers;
	}

	/**
	 * This method is used to download the file from another peer and save it to
	 * local directory
	 * 
	 * @param peer
	 * @param fileName
	 */
	public void downloadFile(PeerObject peer, String fileName) {
		try (Socket clientSocket = new Socket(InetAddress.getByName(peer
				.getIpAddress()), peer.getPort());) {
			TransferObject to = new TransferObject();
			to.setRequestFile(true);
			to.setRequestFileName(fileName);
			UtilityClass.writeObject(to, clientSocket);
			try (BufferedInputStream in = new BufferedInputStream(
					clientSocket.getInputStream());
					DataInputStream din = new DataInputStream(in);) {
				if (din != null) {
					String path = directory + "/" + fileName;
					File file = new File(path);
					if (file.exists()) {
						System.out.println("file already exists");
					} else {
						Files.copy(din, Paths.get(path));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
