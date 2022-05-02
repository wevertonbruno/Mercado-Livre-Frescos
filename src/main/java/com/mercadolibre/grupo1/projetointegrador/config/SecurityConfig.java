package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.filters.JWTValidationFilter;
import com.mercadolibre.grupo1.projetointegrador.services.NoEncoder;
import com.mercadolibre.grupo1.projetointegrador.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

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
    private final JWTUtils jwtUtils;

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
        return (request, response, ex) -> exceptionResolver.resolveException(request, response, null, ex);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(AGENT_REQUESTS).hasRole("AGENT")
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilter(getValidationFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private JWTValidationFilter getValidationFilter() throws Exception {
        return new JWTValidationFilter(authenticationManagerBean(), userDetailsService, jwtUtils, exceptionResolver);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
