package com.roberto.adocao_pets_spring_web.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.ES256);
    private static final long EXPIRATION_TIME = 86400L;

    public static String generateToken(String cpf) {
        return Jwts.builder()
                .setSubject(cpf)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();
    }

    public static String extractCpf(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch(JwtException e){
            return false;
        }
    }
}
