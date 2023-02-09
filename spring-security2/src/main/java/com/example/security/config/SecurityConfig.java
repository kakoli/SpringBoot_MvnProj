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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Setting up custom filter and custom user details service
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    @Autowired //Bean defined in UserDAO
    private UserDetailsService inMemUserService;

    @Autowired // Bean defined in SpringSecurityApp2
    private PasswordEncoder encoder;

    @Autowired
    private  JwtTokenFilter jwtFilter;

    @Bean //Needed since AuthMgr is not Component/Service
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        log.info("Setting the userDetails service in SecurityConfig.authenticationManager");
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                // If not called here, call userDetailsService.loadUserByUsername from JwtFilter.doFilterInternal
                .userDetailsService(inMemUserService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    // Alternate way to inject AuthMgr
    public AuthenticationManager authenticationManager2(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    //Will override the SpringBootWebSecurityConfiguration.defaultSecurityFilterChain object to load request matchers and filters.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("In securityFilterChain with HttpSecurity");
        //http.userDetailsService(customService);
        http.csrf().disable() // disabling csrf since we won't use the default form login.
                // giving every permission to every request for /login endpoint
                .authorizeRequests().antMatchers("/**/jwt/**").permitAll()
                // for everything else, user has to be authenticated
                .anyRequest().authenticated()
                .and()
                //decision creation policy
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //For endpoints other than login, add jwtFilter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* Not used - By this, AuthN provider gets to know the custom user service which is to be used instead of the default one.
    And the manager encodes the pwd in the backgnd, hence needs to know about the encoder.
     */
    //@Bean
    public AuthenticationProvider authenticationProvider() {
        log.info("In authenticationProvider");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(inMemUserService);
        authProvider.setPasswordEncoder(encoder);

        return authProvider;
    }
}
