package edu.iit.cs550;

import edu.iit.cs550.core.Peer;

public class StartPeer {

	public static void main(String[] args) {
		Peer peer = new Peer(2346);
		peer.execute();
		peer.put("1", "2");
		peer.put("2", "2");
		peer.put("3", "2");
		peer.put("4", "2");
		peer.put("5", "2");
		System.out.println(peer.get("1"));
		peer.remove("1");
		System.out.println(peer.get("1"));
		System.out.println(peer.get("2"));
		System.out.println(peer.get("3"));
		System.out.println(peer.get("4"));
		System.out.println(peer.get("5"));
		
	}
}
