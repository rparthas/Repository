package com.SMA.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SMA.entity.User;
import com.SMA.mapper.UserMapper;

@RestController
public class UserController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserMapper userMapper;

	@RequestMapping("/addUser")
	public String addUser(User user) {
		String message = "usuario agregado con exito";
		User existing = userMapper.findUser(user.getUsername());
		logger.info("Existing user:" + existing);
		if (existing == null) {
			userMapper.insertUser(user);
		} else {
			message = "usuario ya existe";
		}
		return message;
	}

	@RequestMapping("/deleteUser")
	public String test() {
		return "test";
	}
}
