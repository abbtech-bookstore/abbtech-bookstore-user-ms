package org.abbtech.practice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.abbtech.practice.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JWTService {

    private static final String SECRET_KEY = "your_secret_key";

    public String generateToken(Optional<User> user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.get().getId());
        claims.put("email", user.get().getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.get().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
