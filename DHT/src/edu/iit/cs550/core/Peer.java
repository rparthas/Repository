package edu.iit.cs550.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.iit.cs550.util.UtilityClass;

/**
 * Main Class for the Peer
 * 
 * @author Raj
 *
 */
public class Peer implements Runnable {

	public Peer(int port) {
		this.peerObect = new PeerObject(UtilityClass.getMyIP(), port);
	}

	PeerObject peerObect = null;

	Map<Object, Object> internalMap = new LinkedHashMap<Object, Object>();

	/**
	 * Computes the distributed hash function for the key
	 * 
	 * @param key
	 * @return
	 */
	private int computeHash(Object key) {
		int hashCode = key.hashCode();
		return (hashCode % UtilityClass.getNoOfPeers()) + 1;
	}

	/**
	 * Main method for putting key and value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(Object key, Object value) {
		boolean success = false;
		int peerNo = computeHash(key);
		String peer = "peer" + peerNo;
		try {
			PeerObject obj = UtilityClass.getPeer(peer);
			if (peerObect.equals(obj)) {
				internalMap.put(key, value);
				success = true;
			} else {
				DataObject object = new DataObject(key, value, "PUT");
				object = connectToPeer(obj, object);
				if (object != null && object.isSuccess()) {
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;

	}

	/**
	 * Main method for getting value from key
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		Object value = null;
		int peerNo = computeHash(key);
		String peer = "peer" + peerNo;
		try {
			PeerObject obj = UtilityClass.getPeer(peer);
			if (peerObect.equals(obj)) {
				value = internalMap.get(key);
			} else {
				DataObject object = new DataObject(key, null, "GET");
				object = connectToPeer(obj, object);
				if (object != null && object.isSuccess()) {
					value = object.getValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	/**
	 * Main method for removing key
	 * 
	 * @param key
	 * @return
	 */
	public boolean remove(Object key) {
		Object value = null;
		int peerNo = computeHash(key);
		String peer = "peer" + peerNo;
		try {
			PeerObject obj = UtilityClass.getPeer(peer);
			if (peerObect.equals(obj)) {
				value = internalMap.remove(key);
			} else {
				DataObject object = new DataObject(key, null, "REM");
				object = connectToPeer(obj, object);
				if (object != null && object.isSuccess()) {
					value = object.getValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value != null;

	}

	public DataObject connectToPeer(PeerObject peerObject, DataObject object) {
		DataObject result = null;
		try (Socket clientSocket = new Socket(InetAddress.getByName(peerObject
				.getIpAddress()), peerObject.getPort());
				ObjectOutputStream oos = new ObjectOutputStream(
						clientSocket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(
						clientSocket.getInputStream());) {
			oos.writeObject(object);
			oos.flush();
			result = (DataObject) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void run() {
		try (ServerSocket socket = new ServerSocket(peerObect.getPort());) {
			int threads = Integer.parseInt(UtilityClass.getValue("THREADS"));
			ExecutorService executorService = Executors
					.newFixedThreadPool(threads);
			while (true) {
				Socket peerSocket = socket.accept();
				PeerThread pThread = new PeerThread();
				pThread.setInternalMap(internalMap);
				pThread.setPeerSocket(peerSocket);
				executorService.submit(pThread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		new Thread(this).start();
	}

}

class PeerThread implements Runnable {

	public Socket peerSocket;

	Map<Object, Object> internalMap = new LinkedHashMap<Object, Object>();

	public Socket getPeerSocket() {
		return peerSocket;
	}

	public void setPeerSocket(Socket peerSocket) {
		this.peerSocket = peerSocket;
	}

	public Map<Object, Object> getInternalMap() {
		return internalMap;
	}

	public void setInternalMap(Map<Object, Object> internalMap) {
		this.internalMap = internalMap;
	}

	@Override
	public void run() {
		try (ObjectInputStream ois = new ObjectInputStream(
				peerSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(
						peerSocket.getOutputStream());) {
			DataObject input = (DataObject) ois.readObject();
			switch (input.getOperation()) {
			case "GET":
				input.setValue(internalMap.get(input.getKey()));
				input.setSuccess("Y");
				break;
			case "PUT":
				internalMap.put(input.getKey(), input.getValue());
				input.setSuccess("Y");
				break;
			case "REM":
				input.setValue(internalMap.remove(input.getKey()));
				input.setSuccess("Y");
				break;
			}
			oos.writeObject(input);
			oos.flush();
			System.out.println(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
