package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.client.ServiceInstance;

@RestController
//@RibbonClient(name = "ping-a-server", configuration = RibbonConfiguration.class)
public class Microservice3 {
	
	@Autowired
	private LoadBalancerClient loadBalancer;
	
	@GetMapping("/")
	public String hello() {
		ServiceInstance serviceInstance = loadBalancer.choose("name-of-the-microservice1");
		System.out.println(serviceInstance.getUri());
		String microservice1Address = serviceInstance.getUri().toString();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(microservice1Address, String.class);
		return "I am the microservice 3. When I ask the microservice1, the Ribbon load balancer find the host " + serviceInstance.getUri() + ". Then, when a call the microservice on this host, it returns: " + response.getBody();
	}

}
