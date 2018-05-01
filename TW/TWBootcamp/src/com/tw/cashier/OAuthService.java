package com.tw.cashier;

public class OAuthService extends LoginService {

	public void login(User user) {
		System.out.println("oauth login");
	}
	
	public void logout(User user) {
		System.out.println("oauth logout");
	}
}
