package com.SMA.entity;


public class User {

	
	public User(String username, String password, int roleId, String empId) {
		super();
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.empId = empId;
	}
	
	public User(){
		super();
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", roleId=" + roleId + ", empId=" + empId + "]";
	}

	private String username;
	
	private String password;
	
	private int roleId;
	
	private String empId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
}
