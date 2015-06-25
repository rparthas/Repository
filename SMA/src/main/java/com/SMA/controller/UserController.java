package com.SMA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SMA.entity.User;
import com.SMA.mapper.UserMapper;

@RestController
public class UserController {

	@Autowired
	UserMapper userMapper;

	@RequestMapping("/addUser")
	public void addUser(User user) {
		userMapper.insertUser(user);

	}

	@RequestMapping("/deleteUser")
	public String test() {
		return "test";
	}
}
