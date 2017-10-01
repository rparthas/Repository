package com.businesshouse.entity;

public class Hotel implements Property {

	PlayerEnhanced owner = null;
	long rent = 50;

	@Override
	public long calculateAmount(PlayerEnhanced owner) {
		if (this.owner != null) {
			if (this.owner.equals(owner)) {
				return 0;
			} else {
				return -rent;
			}
		} else {
			return Long.MAX_VALUE;
		}
	}

	@Override
	public void claimOwnership(PlayerEnhanced owner) {
		this.owner = owner;
	}

}
