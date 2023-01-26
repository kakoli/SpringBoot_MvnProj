package com.example.springdata.emp;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.*")
public class SpringDbConfigClientApp {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(SpringDbConfigClientApp.class, args);
        System.out.println("Started Spring Cloud Config Client");
    }
}
