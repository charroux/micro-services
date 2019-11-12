package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.List;

@RestController
public class Microservice2 {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/")
	@HystrixCommand(fallbackMethod = "defaultMessage")
	public String hello() {
		List<ServiceInstance> instances = discoveryClient.getInstances("name-of-the-microservice1");
		ServiceInstance test = instances.get(0);
		String hostname = test.getHost();
		int port = test.getPort();
		RestTemplate restTemplate = new RestTemplate();
		String microservice1Address = "http://" + hostname + ":" + port;
		ResponseEntity<String> response = restTemplate.getForEntity(microservice1Address, String.class);
		return "I am the microservice 2. When I ask the microservice 1 for a response, it returns: " + response.getBody();
	}
	
	public String defaultMessage() {
		return "Salut !";
	}

}
