package com.example.springdata.emp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Value("${greeting.message}")
    String msg;

    @GetMapping("/msg")
    public String getMsg() {
        return msg;
    }
}
