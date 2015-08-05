package com.SMA.entity;

public class Permissionarios {

	
	public String rfc;
	
	@Override
	public String toString() {
		return "Permissionarios [rfc=" + rfc + ", name=" + name + "]";
	}

	public String name;

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
