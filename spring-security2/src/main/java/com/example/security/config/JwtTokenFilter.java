package com.example.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Custom filter will run once per request. We add this to AuthN Filter Chain
 */
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    //Called before controller
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
        log.info("In JwtTokenFilter.doFilterInternal");
        String authHdr = request.getHeader("Authorization");
        // For login request, just continue
        if(authHdr == null || authHdr.isEmpty() || !authHdr.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        // For /hello, auth needed. Extract the bearer token
        String token = authHdr.split(" ")[1].trim();
        // If not valid, then skip this filter and continue to execute next filter class
        if(!jwtUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtUtil.getUsername(token);
        // 3 parameter constructor used because it sets super.setAuthenticated(true) here.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Finally, give the authentication token to Spring Security Context
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // end of the method, so go for next filter class
        filterChain.doFilter(request, response);
    }
}
