package com.tw.cashier;

public class CurrencyCalculator {


	
	public static void main(String[] args) {
		
		INR inr = new INR(60);
		INR inr2 = new INR(40);
		USD usd = new USD(30);
		inr.add(inr2);
		System.out.println(inr.value+" "+inr.type);
	    usd.add(inr2);
		System.out.println(usd.value+" "+usd.type);
		
		
	}
}
