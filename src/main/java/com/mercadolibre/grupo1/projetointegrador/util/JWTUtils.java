package com.mercadolibre.grupo1.projetointegrador.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {
    private static final Long EXPIRATION_TIME = 86_400_000L;
    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("${security.secret}")
    private String secret;

    public String generateToken(String username){
        String jwt = JWT.create()
                .withSubject(username)
                .withExpiresAt(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .sign(Algorithm.HMAC256(secret));

        return TOKEN_PREFIX + jwt;
    }

    public Boolean isValidToken(String token){
        String username = getSubject(token);
        if(username == null || username.isEmpty()) return false;
        return true;
    }

    public String getSubject(String jwt){
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
            return decodedJWT.getSubject();
        }catch (JWTVerificationException e){
            return null;
        }
    }
}
