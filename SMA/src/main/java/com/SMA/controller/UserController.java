package com.SMA.controller;

import static com.SMA.Constants.USER_ADDED;
import static com.SMA.Constants.USER_DELETED;
import static com.SMA.Constants.USER_EXISTS;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SMA.entity.Response;
import com.SMA.entity.Role;
import com.SMA.entity.User;
import com.SMA.entity.UserRoleMap;
import com.SMA.mapper.UserRoleMapper;

@RestController
public class UserController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRoleMapper userMapper;

	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Response addUser(@RequestBody User user) {
		String message = USER_ADDED;
		User existing = userMapper.findUser(user.getUsername());
		logger.info("Existing user:" + existing);
		if (existing == null) {
			userMapper.insertUser(user);
		} else {
			message = USER_EXISTS;
		}
		updateRoles(user);
		return new Response(message);
	}

	@ResponseBody
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public Response updateUser(@RequestBody User user) {
		userMapper.updateUser(user);
		updateRoles(user);
		return new Response(USER_ADDED);
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public Response deleteUser(@RequestParam("username") String username) {
		userMapper.deleteUserRoles(username);
		userMapper.deleteUser(username);
		return new Response(USER_DELETED);
	}

	@RequestMapping("/getRoles")
	@ResponseBody
	public List<Role> getAllRoles() {
		return userMapper.getAllRoles();
	}

	@RequestMapping("/getUsers")
	@ResponseBody
	public List<User> getAllUsers() {
		List<User> users = userMapper.getAllUsers();
		for (User user : users) {
			List<Role> roles = userMapper.getRolesForUser(user.getUsername());
			List<Integer> roleIds = new ArrayList<Integer>();
			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			}
			user.setRoleIds(roleIds);
		}
		return users;
	}

	private void updateRoles(User user) {
		userMapper.deleteUserRoles(user.getUsername());
		for (Integer roleId : user.getRoleIds()) {
			userMapper.insertRole(new UserRoleMap(user.getUsername(), roleId));
		}
	}
}
