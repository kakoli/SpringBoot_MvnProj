package com.example.springdata.emp;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@ComponentScan({"com.example.persistence.entity.*"})
@EntityScan(basePackages = "com.example.*")
public class SpringDbApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringDbApp.class, args);
        System.out.println("Started Spring Server");
    }
}
