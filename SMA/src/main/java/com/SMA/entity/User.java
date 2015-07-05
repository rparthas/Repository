package com.SMA.entity;

import java.util.List;


public class User {



	

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", roleIds=" + roleIds + ", empId=" + empId + "]";
	}

	private String username;
	
	private String password;
	
	private List<Integer> roleIds;
	
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

	

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
}
