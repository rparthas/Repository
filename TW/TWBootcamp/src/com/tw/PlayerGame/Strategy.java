package com.tw.PlayerGame;

abstract class Strategy {

	State state = null;

	public Strategy(State state) {
		this.state = state;
	}

	abstract public State decideNextState();
}
