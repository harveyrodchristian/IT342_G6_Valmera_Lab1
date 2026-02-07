package com.example.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "change_this_secret_for_prod";
    private static final long EXP_MS = 24 * 60 * 60 * 1000; // 24h

    public static String generateToken(Long userId, String email) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
