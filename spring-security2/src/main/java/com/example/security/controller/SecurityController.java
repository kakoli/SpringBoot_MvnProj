package com.example.security.controller;

import com.example.security.config.JwtUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SecurityController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth/jwt")
    /* {"sub": "test",
	"iss": "kakoli.sample",
	"iat": 1675319135,
	"exp": 1675319435
    }*/
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        /* Creating token object from (user,pwd)to send it to authentication manager.
         AuthN manager encodes the pwd in the backgnd, hence needs to know about the encoder.
         Hence, encoder is injected into SecurityConfig.authenticationManager. */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                                loginDTO.getUsername(), loginDTO.getPassword());
        log.info("Created token " +token.toString());
        authManager.authenticate(token); // calls the UserDetailsService set in authMgr
        log.info("Token authenticated" );
        // If authN success as impl in UserDetailService, generate token and give it to user.
        String jwtToken = jwtUtil.generateToken(loginDTO.getUsername());
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @GetMapping("/auth/hello")
    public ResponseEntity<String> sayHello() {;
        log.info("In hello");
        return new ResponseEntity<>("Hello from security with JWT token", HttpStatus.OK);
    }
}

@Data
class LoginDTO {
    private String username;
    private String password;
}
