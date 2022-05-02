package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.LoginDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String AUTH_URL = "http://localhost:8080/api/v1/auth";

    @Test
    @DisplayName("Testa se é possível fazer login")
    public void itShouldLoggingWithValidUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("seller1");
        loginDTO.setPassword("123456");

        String credentials = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post(AUTH_URL).contentType(MediaType.APPLICATION_JSON).content(credentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", startsWith("Bearer ")));
    }

    @Test
    @DisplayName("Testa se um bad request é retornado ao logar com credenciais inválidas")
    public void itShouldReturnABadRequestInvalidCredentials() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("seller1");
        loginDTO.setPassword("123454");

        String credentials = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post(AUTH_URL).contentType(MediaType.APPLICATION_JSON).content(credentials))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message",  is("Usuário e/ou senha inválidos!")));
    }
}
