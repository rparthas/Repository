package com.tw.cashier;

public class Membership {

	
	
	
	LoginService service ;
	
	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public void register(User user) {
		service.register(user);
		
	}
	
	public void login(User user) {
		service.login(user);
	}
	
	public void logout(User user) {
		service.logout(user);
	}
	
	public void forgotPassword(User user) {
		service.forgotPassword(user);
	}
	
}
