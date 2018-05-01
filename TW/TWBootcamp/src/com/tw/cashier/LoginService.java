package com.tw.cashier;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginService {

	Map<String,User> users = new LinkedHashMap<>();
	
	public void register(User user) {
		if(user.getUsername() != null && user.getPassword() != null && user.getSecret() != null) {
			users.put(user.getUsername(), user);
			System.out.println("user registered");
		}else {
			System.out.println("Not all details provided");
		}
		
		
	}
	
	public void login(User user) {
		User existing = users.get(user.getUsername());
		if(existing != null && existing.getPassword().equals(user.getPassword())) {
			System.out.println("user["+user.getUsername()+"] logged in");
			existing.setLoggedIn(true);
		}else {
			System.out.println("user["+user.getUsername()+"] unable to log in");
		}
	}
	
	public void logout(User user) {
		User existing = users.get(user.getUsername());
		if(existing != null && existing.getPassword().equals(user.getPassword())) {
			System.out.println("user["+user.getUsername()+"] logged out");
			existing.setLoggedIn(false);
		}else {
			System.out.println("user["+user.getUsername()+"] unable to log out");
		}
	}
	
	public void forgotPassword(User user) {
		User existing = users.get(user.getUsername());
		if(existing != null && existing.getSecret().equals(user.getSecret())) {
			existing.setPassword(user.getPassword());
		}else {
			System.out.println("user["+user.getUsername()+"] secret did not match");
		}
	}
}
