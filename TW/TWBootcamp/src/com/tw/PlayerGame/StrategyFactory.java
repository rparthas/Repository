package com.tw.PlayerGame;

public class StrategyFactory {

	public Strategy generateStrategy(State state) {
		Strategy strategy = null;

		switch (state) {
		case CHEAT:
		case COOL:
			strategy = new SimpleStrategy(state);
			break;

		case GRUDGE:
			strategy = new GrudgeStrategy(state);
			break;

		default:
			return null;

		}

		return strategy;

	}
}
