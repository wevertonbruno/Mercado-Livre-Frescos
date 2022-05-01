package com.mercadolibre.grupo1.projetointegrador.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.filters.JWTAuthenticationFilter;
import com.mercadolibre.grupo1.projetointegrador.filters.JWTValidationFilter;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import com.mercadolibre.grupo1.projetointegrador.services.NoEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletOutputStream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String BASE_URL = "/api/v1/fresh-products";
    private static final String[] AGENT_REQUESTS = {
            BASE_URL + "/inboundorder",
            BASE_URL + "/inboundorder/**"
    };
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final AuthService authService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new NoEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            exceptionResolver.resolveException(request, response, null, ex);
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, ex) -> {
            exceptionResolver.resolveException(request, response, null, ex);
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(AGENT_REQUESTS).hasRole("AGENT")
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(getValidationFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private JWTAuthenticationFilter getAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter("/api/v1/auth", authenticationManagerBean(), objectMapper, authService, authenticationFailureHandler());
        return filter;
    }

    private JWTValidationFilter getValidationFilter() throws Exception {
        return new JWTValidationFilter(authenticationManagerBean(), authService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
