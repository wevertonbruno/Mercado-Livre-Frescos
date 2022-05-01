package com.mercadolibre.grupo1.projetointegrador.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.LoginDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.TokenDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final AuthService authService;

    public JWTAuthenticationFilter(String loginUrl, AuthenticationManager authenticationManager, ObjectMapper objectMapper, AuthService authService, AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationManager(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginUrl));
        super.setAuthenticationFailureHandler(failureHandler);
        //super.setFilterProcessesUrl(loginUrl);
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO credentials = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("Authentication failed.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // get user from principal
        AuthenticableUser user = (AuthenticableUser) authentication.getPrincipal();
        String token = authService.generateToken(user);

        TokenDTO dto = new TokenDTO(token);

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}