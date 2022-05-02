package com.mercadolibre.grupo1.projetointegrador.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import com.mercadolibre.grupo1.projetointegrador.services.UserService;
import com.mercadolibre.grupo1.projetointegrador.util.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userService;
    private final JWTUtils jwtUtils;
    private final HandlerExceptionResolver resolver;

    public JWTValidationFilter(AuthenticationManager authenticationManager, UserDetailsService userService, JWTUtils jwtUtils, HandlerExceptionResolver resolver) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            UserDetails authenticatedUser = getAuthenticatedUser(request);
            if(authenticatedUser == null){
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        }catch (UsernameNotFoundException e){
            resolver.resolveException(request, response, null, e);
        }

    }

        public UserDetails getAuthenticatedUser(HttpServletRequest request){
            String token = request.getHeader(AUTHORIZATION_HEADER);
            if(token == null || !token.startsWith(TOKEN_PREFIX)) return null;

            String jwt = token.replace(TOKEN_PREFIX, "").trim();
            if(!jwtUtils.isValidToken(jwt)) throw new UsernameNotFoundException("Token inválido!");

            String username = jwtUtils.getSubject(jwt);
            return userService.loadUserByUsername(username);
        }
}