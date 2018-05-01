package com.tw.cashier;

public class Spike extends Task{

	@Override
	public void close() {
		this.status="END";
		System.out.println("spike closed");
		
	}

	
}
