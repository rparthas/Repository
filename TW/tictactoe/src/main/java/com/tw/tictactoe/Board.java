package com.tw.tictactoe;

public class Board {

	private int[][] arr = null;
	private int moves = 0;

	private Type winner = null;

	public Board() {
		arr = new int[3][3];
	}

	public int getTotalCells() {
		return arr.length * arr.length;
	}

	public boolean isEmpty() {
		return moves == 0;
	}

	private boolean checkValidMoveForPlayer(Type player) {
		return player == Type.HUMAN ? moves % 2 == 0 : moves % 2 == 1;
	}

	public boolean movePlayer(int row, int column, Type player) {
		if (isValidPosition(row, column) && getPlayer(row, column) == 0 && checkValidMoveForPlayer(player)
				&& !isGameOver()) {
			arr[row][column] = player.value;
			moves++;
			computeBoardStatus(row, column, player);
			return true;
		}
		return false;

	}

	private boolean isValidPosition(int row, int column) {
		return (row < 3 && column < 3 && row >= 0 && column >= 0);
	}

	public int getPlayer(int row, int column) {
		return arr[row][column];
	}

	public boolean isGameOver() {
		return moves == 9 || winner != null;
	}

	private boolean columnWiseCheck(int column) {
		int value = arr[0][column];
		if (value == 0) {
			return false;
		}
		for (int i = 1; i < arr.length; i++) {
			if (arr[i][column] != value) {
				return false;
			}
		}
		return true;
	}

	private boolean rowWiseCheck(int row) {
		int value = arr[row][0];
		if (value == 0) {
			return false;
		}
		for (int i = 1; i < arr.length; i++) {
			if (arr[row][i] != value) {
				return false;
			}
		}
		return true;
	}

	private boolean diagonalCheck() {

		return (arr[1][1] != 0 && ((arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2])
				|| (arr[2][0] == arr[1][1] && arr[1][1] == arr[0][2])));

	}

	private void computeBoardStatus(int row, int column, Type player) {
		if (rowWiseCheck(row) || columnWiseCheck(column) || diagonalCheck()) {
			winner = player;
		}
	}

	public Type getWinner() {
		return winner;
	}

}
