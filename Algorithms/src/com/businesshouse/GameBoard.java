package com.businesshouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.businesshouse.entity.Constants;
import com.businesshouse.entity.Constants.BOARD;
import com.businesshouse.entity.Player;

public class GameBoard {

	public final static BOARD[] board = { BOARD.E, BOARD.E, BOARD.J, BOARD.H, BOARD.E, BOARD.T, BOARD.J, BOARD.T,
			BOARD.E, BOARD.E, BOARD.H, BOARD.J, BOARD.T, BOARD.H, BOARD.E, BOARD.E, BOARD.J, BOARD.H, BOARD.E, BOARD.T,
			BOARD.J, BOARD.T, BOARD.E, BOARD.E, BOARD.H, BOARD.J, BOARD.T, BOARD.H, BOARD.J, BOARD.E, BOARD.E, BOARD.J,
			BOARD.H, BOARD.E, BOARD.T, BOARD.J, BOARD.T, BOARD.E, BOARD.E, BOARD.H, BOARD.J, BOARD.T, BOARD.E, BOARD.H,
			BOARD.E };

	public final static int boardLength = board.length;

	private static final Map<Integer, Integer> ownedHotels = new HashMap<>();

	public static void claimHotel(int id, int position) {
		ownedHotels.put(position, id);
	}

	public static long checkRent(int id, int boardPosition) {
		BOARD boardObject = board[boardPosition];
		if (boardObject.equals(BOARD.H)) {
			if (ownedHotels.containsKey(boardPosition)) {
				Integer owner = ownedHotels.get(boardPosition);
				if (id == owner) {
					return 0;
				} else {
					players.get(owner-1).payRent(-BOARD.H.getRent());
					return boardObject.getRent();
				}
			} else {
				return Constants.CLAIM_HOTEL_AMOUNT;
			}
		} else {
			return boardObject.getRent();
		}
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
			IntStream.rangeClosed(0, playerCount - 1).forEach(id -> {
				players.get(id).printBalance();
			});
			counter++;
		}

		

	}
}
