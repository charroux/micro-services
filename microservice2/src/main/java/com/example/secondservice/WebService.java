package com.example.secondservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WebService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancer;

    @GetMapping("/")
    @HystrixCommand(fallbackMethod = "defaultMessage")
    public String method(){
        List<ServiceInstance> instances = discoveryClient.getInstances("name-of-the-microservice1");
        ServiceInstance test = instances.get(0);
        String hostname = test.getHost();
        int port = test.getPort();
        RestTemplate restTemplate = new RestTemplate();
        String microservice1Address = "http://" + hostname + ":" + port;
        System.out.println(microservice1Address);
        ResponseEntity<String> response = restTemplate.getForEntity(microservice1Address, String.class);
        String s = response.getBody();
        return s;
    }

    @GetMapping("/loadBalencer")
    public String loadBalancing(){
        ServiceInstance serviceInstance= loadBalancer.choose("name-of-the-microservice1");
        System.out.println(serviceInstance.getUri());
        String hostname = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        RestTemplate restTemplate = new RestTemplate();
        String microservice1Address = "http://" + hostname + ":" + port;
        System.out.println(microservice1Address);
        ResponseEntity<String> response = restTemplate.getForEntity(microservice1Address, String.class);
        String s = response.getBody();
        return s;
    }

    public String defaultMessage(){
        return "default message";
    }
}
