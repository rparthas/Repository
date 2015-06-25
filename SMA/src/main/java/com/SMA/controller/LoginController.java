package com.SMA.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping("/login")
	public Principal login(Principal user) {
		return user;
	}

	@RequestMapping("/test")
	public String test() {
		return "test";
	}
}
