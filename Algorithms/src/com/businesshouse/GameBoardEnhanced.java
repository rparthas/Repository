package com.businesshouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.businesshouse.entity.Constants;
import com.businesshouse.entity.Empty;
import com.businesshouse.entity.Hotel;
import com.businesshouse.entity.Jail;
import com.businesshouse.entity.Player;
import com.businesshouse.entity.Property;
import com.businesshouse.entity.Treasure;

public class GameBoardEnhanced {

	public final static Property[] board = { new Empty(), new Empty(), new Jail(), new Hotel(), new Empty(), new Treasure(), new Jail(), new Treasure(),
			new Empty(), new Empty(), new Hotel(), new Jail(), new Treasure(), new Hotel(), new Empty(), new Empty(), new Jail(), new Hotel(), new Empty(), new Treasure(),
			new Jail(), new Treasure(), new Empty(), new Empty(), new Hotel(), new Jail(), new Treasure(), new Hotel(), new Jail(), new Empty(), new Empty(), new Jail(),
			new Hotel(), new Empty(), new Treasure(), new Jail(), new Treasure(), new Empty(), new Empty(), new Hotel(), new Jail(), new Treasure(), new Empty(), new Hotel(),
			new Empty() };

	public final static int boardLength = board.length;

	private static final Map<Integer, Integer> ownedHotels = new HashMap<>();

	public static void claimHotel(int id, int position) {
		ownedHotels.put(position, id);
	}

	public static Property getProperty(int boardPosition) {
		Property property = board[boardPosition];
		return property;
	}

	private final static List<Player> players = new ArrayList<>();

	public static void main(String[] args) {

		int playerCount = 3;
		int[] diceRolls = { 4, 4, 4, 6, 7, 8, 5, 11, 10, 12, 2, 3, 5, 6, 7, 8, 5, 11, 10, 12, 2, 3, 5, 6, 7, 8, 5, 11,
				10, 12 };

		IntStream.rangeClosed(1, playerCount).forEach(id -> {
			players.add(new Player(id, 1000));
		});
		int counter = 0;
		for (int dieIndex = 0; dieIndex < diceRolls.length; dieIndex++) {
			int die = diceRolls[dieIndex];
			Player player = players.get(counter % playerCount);
			player.move(die);
			System.out.println("player " + player.getId() + "die:" + die + "position:" + player.getBoardPosition()
					+ "balance:" + player.getBalance());
			counter++;
		}

		IntStream.rangeClosed(0, playerCount - 1).forEach(id -> {
			players.get(id).printBalance();
		});

	}
}
