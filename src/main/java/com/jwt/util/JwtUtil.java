package com.jwt.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    @Value("${secret.key}")
    private String SECRET_KEY;

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Krishna")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    public Boolean validateToken(String token, String username) {
        final String usernameInToken = getUsername(token);
        return (usernameInToken.equals(username) && !isTokenExpired(token));
    }

}
