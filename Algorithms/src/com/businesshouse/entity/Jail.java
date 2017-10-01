package com.businesshouse.entity;

public class Jail implements Property {

	
	@Override
	public long calculateAmount(PlayerEnhanced owner) {
		return -50;
	}

}
