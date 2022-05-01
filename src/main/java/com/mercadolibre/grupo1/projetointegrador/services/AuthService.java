package com.mercadolibre.grupo1.projetointegrador.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;

@Service
public class AuthService {
    private static final Long EXPIRATION_TIME = 86_400_000L;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private UserService userService;

    @Value("${security.secret}")
    private String secret;

    public String generateToken(AuthenticableUser user){
        String jwt = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .sign(Algorithm.HMAC256(secret));

        return TOKEN_PREFIX + jwt;
    }

    public UserDetails getAuthenticatedUser(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if(token == null || !token.startsWith(TOKEN_PREFIX)) return null;

        String jwt = token.replace(TOKEN_PREFIX, "").trim();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
        String username = decodedJWT.getSubject();
        if(username == null) return null;

        return userService.loadUserByUsername(username);
    }

    public <T> T getPrincipalAs(Class<T> F){
        AuthenticableUser principal = (AuthenticableUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (T) userService.loadUserByUsername(principal.getUsername());
    }

}
