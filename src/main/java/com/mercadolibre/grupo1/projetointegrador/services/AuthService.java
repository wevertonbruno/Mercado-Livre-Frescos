package com.mercadolibre.grupo1.projetointegrador.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mercadolibre.grupo1.projetointegrador.dtos.LoginDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.TokenDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.exceptions.BadCredentialsException;
import com.mercadolibre.grupo1.projetointegrador.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Value("${security.secret}")
    private String secret;

    public TokenDTO login(LoginDTO login){
        //logica para autenticar usuario
        UserDetails user = userService.loadUserByUsername(login.getUsername());
        String encodedPassword = passwordEncoder.encode(login.getPassword());

        if(!encodedPassword.equals(user.getPassword())){
            throw new BadCredentialsException("Senha inválida!");
        }

        String token = jwtUtils.generateToken(user.getUsername());

        return new TokenDTO(token);
    }

    public <T> T getPrincipalAs(Class<T> F){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (T) userService.loadUserByUsername(principal.getUsername());
    }

    public AuthenticableUser getPrincipal(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (AuthenticableUser) userService.loadUserByUsername(principal.getUsername());
    }


}
