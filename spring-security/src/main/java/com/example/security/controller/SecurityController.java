package com.example.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SecurityController {

    @GetMapping("/auth/basic")
    public ResponseEntity<String> sayHello() {;
        log.info("In basic");
        return new ResponseEntity<>("Hello from security with Basic Authentication", HttpStatus.OK);
    }
}
