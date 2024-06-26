package com.Zer0Rx.paymentsystem.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Zer0Rx.paymentsystem.config.exceptions.InvalidTokenException;
import com.Zer0Rx.paymentsystem.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
    @Value("$${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("auth").withSubject(user.getEmail()).withExpiresAt(ExpirationDate()).sign(algorithm);
            return token;
        }catch(JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token", exception);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();
        }catch(JWTVerificationException exception){
            throw new InvalidTokenException("Invalid Token");
        }
    }

    private Instant ExpirationDate(){
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
    }

}
