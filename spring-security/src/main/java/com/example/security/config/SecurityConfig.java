package com.example.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Setting up custom filter and custom user details service
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    @Autowired
    private UserDetailsService customService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private  JwtTokenFilter jwtFilter;

    @Bean //Needed since AuthMgr is not Component/Service
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        log.info("Setting the userDetails service in SecurityConfig.authenticationManager");
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    // Alternate way to inject AuthMgr
    public AuthenticationManager authenticationManager2(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /* Not used - By this, AuthN provider gets to know the custom user service which is to be used instead of the default one.
    And the manager encodes the pwd in the backgnd, hence needs to know about the encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customService);
        authProvider.setPasswordEncoder(encoder);

        return authProvider;
    }

    @Bean
    //Will build a DefaultSecurityFilterChain object to load request matchers and filters.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("In securityFilterChain with HttpSecurity");
        //http.userDetailsService(customService);
        http.csrf().disable() // disabling csrf since we won't use the default form login.
                // giving every permission to every request for /login endpoint
                .authorizeRequests().antMatchers("/**/login/**").permitAll()
                // for everything else, user has to be authenticated
                .anyRequest().authenticated()
                .and()
                //decision creation policy
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //For endpoints other than login
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
