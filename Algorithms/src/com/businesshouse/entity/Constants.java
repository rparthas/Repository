package com.businesshouse.entity;

public class Constants {
	
	public static final long CLAIM_HOTEL_AMOUNT=Long.MAX_VALUE;
	
	public enum BOARD{
		H(-50,200),
		T(200,0),
		J(-150,0),
		E(0,0);
		
		private long rent;
		
		public long getRent() {
			return rent;
		}

		public long getPrice() {
			return price;
		}

		

		private long price;
		
		
		BOARD(long rent,long price){
			this.rent=rent;
			this.price=price;
		}
	}

	
}
