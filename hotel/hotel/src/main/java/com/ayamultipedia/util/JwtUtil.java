package com.ayamultipedia.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    //  THIS METHOD GENERATE TOKEN
    private String generateToken(Map<String, Object> extraClaims, UserDetails details) {
        return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*7))
                .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }



    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigningKey() {
        //byte[] keyBytes = Decoders.BASE64.decode("eyJhbGciOiJIUzI1NiJ9e30FXibJVNHsvJ6FfN9XtTTom9cGExRqsldHbrhAOqRUg");
        byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B625065536B566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
