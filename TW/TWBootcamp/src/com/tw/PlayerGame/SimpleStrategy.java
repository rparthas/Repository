package com.tw.PlayerGame;

public class SimpleStrategy extends Strategy {

	public SimpleStrategy(State state) {
		super(state);
	}

	@Override
	public State decideNextState() {
		return state;

	}

}
