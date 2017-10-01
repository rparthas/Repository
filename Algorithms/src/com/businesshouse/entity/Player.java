package com.businesshouse.entity;

import java.util.ArrayList;
import java.util.List;

import com.businesshouse.GameBoard;
import com.businesshouse.entity.Constants.BOARD;

public class Player {

	private int id;

	public Player(int id, int balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	
	private List<Integer> hotels = new ArrayList<>();

	private int balance;

	private int boardPosition=-1;

	public int getBoardPosition() {
		return boardPosition;
	}

	public void setBoardPosition(int boardPosition) {
		this.boardPosition = boardPosition;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	public void payRent(long amount){
		balance+=amount;
	}

	public void move(int die) {
		boardPosition = boardPosition + die;
		if (boardPosition >= GameBoard.boardLength) {
			boardPosition = boardPosition - GameBoard.boardLength;
		}

		long rent = GameBoard.checkRent(id, boardPosition);
		if (rent == Constants.CLAIM_HOTEL_AMOUNT && balance > BOARD.H.getPrice()) {
			GameBoard.claimHotel(id, boardPosition);
			hotels.add(boardPosition);
			balance-=BOARD.H.getPrice();
		} else {
			balance += rent;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void printBalance(){
		long totalWorth=balance + (hotels.size() * BOARD.H.getPrice());
		System.out.println("Player "+ id+" has totalWorth "+totalWorth);
	}
}
