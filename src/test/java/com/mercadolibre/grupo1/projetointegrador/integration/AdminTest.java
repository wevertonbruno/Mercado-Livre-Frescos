package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.StateDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.LoginDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AdminTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ADMIN_URL = "/api/v1/admin";

    @Test
    @DisplayName("Testa se o administrador consegue atribuir um cargo a um usuário")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void itShouldAssignARoleToUser() throws Exception {
        mockMvc.perform(put(ADMIN_URL + "/users/4/assign/ROLE_SELLER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles", containsInAnyOrder("ROLE_SELLER")));
    }

    @Test
    @DisplayName("Testa se o administrador consegue inativar um usuario")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void itShouldInactivateAUser() throws Exception {

        String payload = "{\"active\": \"false\"}";

        mockMvc.perform(put(ADMIN_URL + "/users/4/enabled").contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));
    }
}
