package com.SMA.entity;

public class UserRoleMap {

	private String username;
	
	private Integer roleId;

	public String getUsername() {
		return username;
	}

	public UserRoleMap(String username, Integer roleId) {
		super();
		this.username = username;
		this.roleId = roleId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
