package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class Microservice2 {
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@GetMapping("/")
	public String hello() {
		InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka("name-of-the-microservice1", false);
		String hostname = instanceInfo.getHostName();
	    int port = instanceInfo.getPort();
		RestTemplate restTemplate = new RestTemplate();
		String microservice1Address = "http://" + hostname + ":" + port;
		ResponseEntity<String> response = restTemplate.getForEntity(microservice1Address, String.class);
		return "I am the microservice 2. When I ask the microservice 1 for a response, it returns: " + response.getBody();
	}

}
