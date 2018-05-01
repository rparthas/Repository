package com.tw.cashier;

public abstract class Currency {

	double value;
	String type="";
	double baseExchange=0.0;

	private double toUSD() {
		return this.value/baseExchange;
	}

	public void add(Currency currency) {
		double base1 = this.toUSD();
		base1 += currency.toUSD();
	    this.value = base1 * baseExchange;
	}
	
	
}
