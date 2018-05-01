package com.tw.cashier;

public class MemberInteractor {

	public static void main(String[] args) {
		User user = new User();
		user.setUsername("Ram");
		user.setPassword("123");
		user.setSecret("name");
		Membership membership = new Membership();
		membership.setService(new OAuthService());
		membership.register(user);
		membership.login(user);
		membership.logout(user);
		membership.forgotPassword(user);

	}

}
