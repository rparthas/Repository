package edu.learn.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {
	
	@Value("${server.port}")
	private int port;

	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String hello() {
		System.out.println("port:"+port);
		return "Do Coding.";
	}
}
