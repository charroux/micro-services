package com.example.secondservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class SecondserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondserviceApplication.class, args);
	}

}
