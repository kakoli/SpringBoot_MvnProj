package com.example.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private BasicAuthenticationEntryPoint authEntryPoint;

    @Bean
    //Will build a DefaultSecurityFilterChain object to load request matchers and filters.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("In securityFilterChain with HttpSecurity");
        //http.userDetailsService(customService);
        http
                .csrf().disable() // disabling csrf since we won't use the default form login.
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                // Calls BasicAuthenticationFilter
                .httpBasic()//Customizer.withDefaults())
                // this is called only on Auth type 'None'
                .authenticationEntryPoint(authEntryPoint);
        return http.build();
    }

    @Bean
    public UserDetailsService initUser() {
        log.info("In UserDetailsService bean");
        UserDetails user = User.builder()
                .username("basic")
                .password(encoder.encode("basic123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean //Needed since AuthMgr is not Component/Service
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        log.info("Setting the userDetails service in SecurityConfig.authenticationManager");
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                //Instead of having separate class which gives bean definition as in UserDAO and then Autowiring it, we can do this.
                // Have bean definition here and call the method itself.
                .userDetailsService(initUser())
                //.passwordEncoder(encoder)
                .and()
                .build();
    }
}
