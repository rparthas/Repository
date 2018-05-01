package com.tw.PlayerGame;

public enum State {

	COOL(-1), CHEAT(0), GRUDGE(-1), COPYCAT(-1), DETECTIVE(-1);

	int value;

	private State(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
