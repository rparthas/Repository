package com.businesshouse.entity;

public class Treasure implements Property {

	boolean increasing = true;
	int visits = 0;
	int position =0;
	
	@Override
	public long calculateAmount(PlayerEnhanced owner) {
		long value = 200;
		if (increasing) {
			visits++;
			if (visits == 3) {
				increasing = false;
			}
		} else {
			value=0;
			visits--;
			if (visits == 0) {
				increasing = true;
			}
		}

		return value;
	}

}
