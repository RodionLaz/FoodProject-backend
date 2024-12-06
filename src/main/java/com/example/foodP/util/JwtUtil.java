package com.example.foodP.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import org.springframework.stereotype.Component;


@Component
public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JwtUtil(){}

    public static Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("UserId", Long.class);
    }

    public static Long extractBusinessId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("businessId", Long.class);
    }
    //customers token
    public static String generateToken(String username, String email,Long userId) {
        return Jwts.builder()
                .claim("username", username)
                .claim("email", email)
                .claim("UserId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    //business token 
    public static String generateToken(String username,Long businessId) {
        return Jwts.builder()
                .claim("username", username)
                .claim("businessId", businessId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
}
