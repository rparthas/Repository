package com.SMA.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SMA.entity.Response;
import com.SMA.entity.Role;
import com.SMA.entity.User;
import com.SMA.mapper.UserRoleMapper;

@RestController
public class UserController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRoleMapper userMapper;

	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Response addUser(@RequestBody User user) {
		String message = "usuario agregado con exito";
		User existing = userMapper.findUser(user.getUsername());
		logger.info("Existing user:" + existing);
		if (existing == null) {
			userMapper.insertUser(user);
		} else {
			message = "usuario ya existe";
		}
		return new Response(message);
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public String test() {
		return "test";
	}

	@RequestMapping("/getRoles")
	@ResponseBody
	public List<Role> getAllRoles() {
		return userMapper.getAllRoles();
	}
}
