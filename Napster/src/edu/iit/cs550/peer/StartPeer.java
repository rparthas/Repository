package edu.iit.cs550.peer;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import edu.iit.cs550.server.PeerObject;
import edu.iit.cs550.utility.Constants;
import edu.iit.cs550.utility.UtilityClass;

public class StartPeer implements Runnable {

	Peer peer = null;

	public static void main(String[] args) {
		Properties prop = UtilityClass.loadProperties();
		int port = Integer.parseInt(prop.getProperty(Constants.PEERPORT));
		String indexServerAddress = prop.getProperty(Constants.SERVERADDRESS);
		int serverPort = Integer.parseInt(prop
				.getProperty(Constants.SERVERPORT));
		String directory = prop.getProperty(Constants.DIRECTORY);
		int threads = Integer.parseInt(prop.getProperty(Constants.PEERTHREADS));
		Peer peer = null;
		StartPeer startPeer = new StartPeer();
		try (Scanner scanner = new Scanner(System.in);) {
			peer = new Peer(port, directory, serverPort, indexServerAddress,
					threads);
			peer.register();
			startPeer.peer = peer;
			new Thread(startPeer).start();
			while (true) {
				System.out.println("1:Lookup File \n2:Exit");
				System.out.println("Please Enter your choice");
				int choice = 0;
				try {
					choice = Integer.parseInt(scanner.nextLine());
					if (choice == 1) {
						boolean fileLoop = true;
						while (fileLoop) {
							System.out.println("Please Enter the filename");
							String fileName = scanner.nextLine();
							if (fileName != null || !"".equals(fileName)) {
								List<PeerObject> peers = peer
										.lookUpFile(fileName);
								if (peers == null || peers.size() == 0) {
									System.out.println("File not found");
									continue;
								}
								int peerId = 0;
								for (PeerObject peerObj : peers) {
									peerId++;
									System.out.println("Peer " + peerId
											+ " Details:" + peerObj);

								}
								choosePeer(peer, scanner, fileName, peers);
								fileLoop = false;
							}
						}

					} else if (choice == 2) {
						peer.shutDown();
						System.exit(0);
					} else {
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to get the peer to download
	 * 
	 * @param peer
	 * @param scanner
	 * @param fileName
	 * @param peers
	 * @param peerLoop
	 */
	private static void choosePeer(Peer peer, Scanner scanner, String fileName,
			List<PeerObject> peers) {
		int peerId = 0;
		boolean peerLoop = true;
		while (peerLoop) {
			System.out.println("Please Enter the peer to connect to");
			try {
				peerId = Integer.parseInt(scanner.nextLine());
				if (peerId <= 0 || peerId > peers.size()) {
					continue;
				} else {
					peer.downloadFile(peers.get(peerId - 1), fileName);
					System.out.println("File Download successful");
					peerLoop = false;
				}

			} catch (Exception e) {
				continue;
			}
		}
	}

	@Override
	public void run() {
		peer.connect();
	}
}
