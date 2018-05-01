package com.tw.cashier;

public class INR extends Currency {
	

	public INR(double value) {
		this.value=value;
		this.type="INR";
		this.baseExchange=60;
	}

	
}
