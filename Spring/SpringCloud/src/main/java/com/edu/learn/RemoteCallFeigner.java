package com.edu.learn;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "client-server")
public interface RemoteCallFeigner {
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String hello();
}
