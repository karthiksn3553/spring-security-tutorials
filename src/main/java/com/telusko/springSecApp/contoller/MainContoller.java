package com.telusko.springSecApp.contoller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainContoller {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "Hello world ,session ID: " + request.getSession().getId();
    }
}
