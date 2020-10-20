package com.example.firstservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebService {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

}
