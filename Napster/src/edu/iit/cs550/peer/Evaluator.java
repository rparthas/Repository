package edu.iit.cs550.peer;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.iit.cs550.server.PeerObject;
import edu.iit.cs550.server.Server;
import edu.iit.cs550.utility.UtilityClass;

public class Evaluator implements Runnable {

	boolean isServer = false;
	Server server = null;
	Peer peer = null;

	public static void main(String args[]) throws Exception {

		Evaluator evaluator = new Evaluator();
		evaluator.server = new Server(5676, 4);
		evaluator.isServer = true;
		new Thread(evaluator).start();
		evaluator = new Evaluator();
		evaluator.peer = new Peer(5678, "E://Media//IIT/peer1", 5676,
				"192.168.56.1", 3);
		evaluator.peer.register();
		new Thread(evaluator).start();
		evaluator = new Evaluator();
		evaluator.peer = new Peer(5679, "E://Media//IIT/peer2", 5676,
				"192.168.56.1", 3);
		evaluator.peer.register();
		new Thread(evaluator).start();
		evaluator = new Evaluator();
		evaluator.peer = new Peer(5680, "E://Media//IIT/peer3", 5676,
				"192.168.56.1", 3);
		evaluator.peer.register();
		new Thread(evaluator).start();
		evaluator.peer = new Peer(5687, "E://Media//IIT/peer4", 5676,
				"192.168.56.1", 3);
		evaluator.peer.register();
		new Thread(evaluator).start();

		long finalTime = 0;
		ExecutorService es = Executors.newFixedThreadPool(100);

		int peer1[] = { 100, 200, 200, 200, 200, 500 };
		int peer2[] = { 100, 100, 200, 300, 500, 500 };
		int peer3[] = { 100, 200, 200, 300, 300, 500 };

		for (int peersize = 0; peersize < 6; peersize++) {
			for (int index = 0; index < 3; index++) {
				cleanDir();
				CountDownLatch latch = new CountDownLatch(peer1[peersize]
						+ peer2[peersize] + peer3[peersize]);
				long startTime = UtilityClass.getTime();
				evaluate(evaluator, "1k", peer1[peersize], es, latch);
				evaluate(evaluator, "2k", peer2[peersize], es, latch);
				evaluate(evaluator, "10k", peer3[peersize], es, latch);
				latch.await();
				finalTime = finalTime + UtilityClass.getTime() - startTime;
			}

			System.out.println(finalTime / 3);
		}

	}

	private static void cleanDir() {
		File file = new File("E://Media//IIT/peer4");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}
	}

	private static void evaluate(Evaluator evaluator, String prefix, int count,
			ExecutorService es, CountDownLatch latch) throws Exception {

		for (int i = 1; i <= count; i++) {
			String file = prefix + i + ".bin";
			ConcurrentDownloader ccd = new ConcurrentDownloader();
			ccd.peer = evaluator.peer;
			ccd.file = file;
			ccd.latch = latch;
			es.execute(ccd);
		}
	}

	@Override
	public void run() {
		try {
			if (isServer) {
				server.connect();
			} else {
				peer.connect();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

class ConcurrentDownloader implements Runnable {

	Peer peer = null;
	String file = null;
	CountDownLatch latch = null;

	@Override
	public void run() {
		try {
			List<PeerObject> peers = peer.lookUpFile(file);
			/*
			 * int index = ThreadLocalRandom.current().nextInt(0, 2);
			 * peer.downloadFile(peers.get(index), file);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			latch.countDown();
		}

	}

}
