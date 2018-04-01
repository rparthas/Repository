package com.edu.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaServer
@EnableCircuitBreaker
@EnableFeignClients
public class AdminApplication {

	public static void main(String[] args) {
		ApplicationContext ctx=SpringApplication.run(AdminApplication.class, args);
	}

}
