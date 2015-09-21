package edu.iit.cs550.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.iit.cs550.utility.UtilityClass;

public class Server implements Callable<Object> {

	private int port = 0;
	private int threadCount = 4;
	private Socket socket = null;
	private Map<String, List<PeerObject>> registry = new LinkedHashMap<String, List<PeerObject>>();

	public Server(int port, int threadCount) {
		this.port = port;
		this.threadCount = threadCount;
	}

	/**
	 * This is the main method for starting the connection
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {
		ExecutorService executorService = Executors
				.newFixedThreadPool(threadCount);
		try (ServerSocket serverSocket = new ServerSocket(port, threadCount);) {
			System.out.println("Server running");
			while (true) {
				socket = serverSocket.accept();
				executorService.submit(this);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	/**
	 * Main Method to process the received object
	 */
	@Override
	public Object call() throws Exception {
		try {
			TransferObject to = UtilityClass.readObject(socket);
			if (to != null) {
				// System.out.println(to.toString());
				if (to.isRequestFile()) {
					List<PeerObject> peers = lookUpFile(to.getRequestFileName());
					to.setPeers(peers);
					UtilityClass.writeObject(to, socket);
				} else {
					saveToRegistry(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method saves the object to registry
	 */
	private void saveToRegistry(TransferObject to) {
		if (to.getFiles() != null) {
			for (String file : to.getFiles()) {
				List<PeerObject> peers = new ArrayList<PeerObject>();
				if (registry.containsKey(file)) {
					peers = registry.get(file);
				}
				PeerObject peer = new PeerObject();
				peer.setDirectory(to.getDirectory());
				peer.setIpAddress(to.getIpAddress());
				peer.setPort(to.getPort());
				peers.add(peer);
				registry.put(file, peers);
			}
		}
	}

	/**
	 * This method returns the Peer list for the files
	 * 
	 * @param fileName
	 */
	private List<PeerObject> lookUpFile(String fileName) {
		List<PeerObject> peers = new ArrayList<PeerObject>();
		if (registry.containsKey(fileName)) {
			peers = registry.get(fileName);
		}
		return peers;
	}

}
