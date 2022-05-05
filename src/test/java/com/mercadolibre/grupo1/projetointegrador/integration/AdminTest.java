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
                .andExpect(jsonPath("$.roles", containsInAnyOrder("ROLE_SELLER", "ROLE_AGENT", "ROLE_CUSTOMER")));
    }

    @Test
    @DisplayName("Testa se o administrador consegue atribuir todos os cargos a um usuário")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void itShouldAssignAllRolesToUser() throws Exception {
        mockMvc.perform(put(ADMIN_URL + "/users/9/assign/ROLE_SELLER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles", containsInAnyOrder("ROLE_SELLER")));

        mockMvc.perform(put(ADMIN_URL + "/users/9/assign/ROLE_AGENT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles", containsInAnyOrder("ROLE_SELLER", "ROLE_AGENT")));

        mockMvc.perform(put(ADMIN_URL + "/users/9/assign/ROLE_CUSTOMER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles", containsInAnyOrder("ROLE_SELLER", "ROLE_AGENT", "ROLE_CUSTOMER")));
    }

    @Test
    @DisplayName("Testa se um erro é retornado ao tentar atribuir uma role inexistente")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void itShouldReturnABadRequestInvalidRole() throws Exception {
        mockMvc.perform(put(ADMIN_URL + "/users/4/assign/ROLE_DELIVERY"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O cargo ROLE_DELIVERY não é aceito!"));
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
