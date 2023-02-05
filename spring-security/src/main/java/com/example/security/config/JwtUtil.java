package com.example.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static final int expireInMs = 300 * 1000; //5 mins

    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("kakoli.sample")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireInMs))
                .signWith(key)
                .compact();
    }

    //Alternate way - Not used
    public String generateToken(Map<String, Object> claims, UserDetails usrDetails) {
        return Jwts.builder()
                .setSubject(usrDetails.getUsername())
                .setIssuer("kakoli.sample")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("authorities", usrDetails.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .signWith(key).compact();
    }

    public boolean validate(String token) {
        boolean ret = false;
        if (getUsername(token) != null ) {
            if (isValid(token))
                ret = true;
        }
        return ret;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);

        return claims.getSubject();
    }

    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
