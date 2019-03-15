package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Microservice1 {
	
	@GetMapping("/")
	public String hello() {
		return "Hello !";
	}

}
