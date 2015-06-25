package com.SMA.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.SMA.Application;
import com.SMA.controller.UserController;
import com.SMA.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserTest {

	@Autowired
	private UserController userController;

	@Test
	public void addUser() {
		User user = new User();
		user.setEmpId(null);
		user.setPassword("hi");
		user.setUsername("hi");
		user.setRoleId(1);
		userController.addUser(user);
	}
}
