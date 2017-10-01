package com.businesshouse.entity;

public interface Property {

	default long calculateAmount(PlayerEnhanced owner){
		return 0;
	};
	
	default void claimOwnership(PlayerEnhanced owner){
		throw new UnsupportedOperationException();
	}
}
