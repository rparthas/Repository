package edu.learn.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(CloudApplication.class, args);

		
	}

	
}