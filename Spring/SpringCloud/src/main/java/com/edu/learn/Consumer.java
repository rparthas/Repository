package com.edu.learn;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class Consumer {

	@Autowired
	private LoadBalancerClient loadBalancer;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RemoteCallFeigner loadBalancerClient;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMsg")
	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public String hello() {
		ServiceInstance serviceInstance=loadBalancer.choose("client-server");

		String baseUrl=serviceInstance.getUri().toString();
		
		baseUrl=baseUrl+"/hello";
		
		//String baseUrl = "http://localhost:8090/hello";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return response.getBody();
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/getCMsg")
	public String helloClient() {
		ServiceInstance serviceInstance=discoveryClient.getInstances("zuul-service").get(0);

		String baseUrl=serviceInstance.getUri().toString();
		
		baseUrl=baseUrl+"client-server/hello";
		
		//String baseUrl = "http://localhost:8090/hello";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			System.out.println(baseUrl);
			response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
			return response.getBody();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return null;
	}
	
	public String getDataFallBack() {
		return "Do coding after break";
	}
	
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSMsg")
	public String helloSimulator() {
		return loadBalancerClient.hello();
	}
}
