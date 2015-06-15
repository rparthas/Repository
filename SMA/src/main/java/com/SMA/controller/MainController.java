package com.SMA.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 	
public class MainController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		System.out.println("test");
		return "test";
	}
}
