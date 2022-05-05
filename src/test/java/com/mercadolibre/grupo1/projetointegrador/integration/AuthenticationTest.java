package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.*;
import com.mercadolibre.grupo1.projetointegrador.services.FakeEmailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String AUTH_URL = "/api/v1/auth";
    private static final String REGISTER_URL = "/api/v1/register";
    private static final String REFRESH_TOKEN_URL = "/api/v1/refresh-token";
    private static final String PROFILE_URL = "/api/v1/me";

    private static final String PASSWORD_RESET_URL = "/api/v1/reset-password";

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
                .andExpect(jsonPath("$.message",  is("Senha inválida!")));
    }

    @Test
    @DisplayName("Testa se um usuário consegue acessar uma rota sem a role correta")
    @WithMockUser(username = "seller1", roles = {"SELLER"})
    public void itShouldReturnAForbiddenException() throws Exception {
        mockMvc.perform(
                        get("/api/v1/fresh-products/due-date?section_code=4&expires_in=15"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Acesso não autorizado!"));
    }

    @Test
    @DisplayName("Testa se um usuário consegue acessar uma rota com um token inválido")
    public void itShouldReturnAAuthenticationException() throws Exception {
        mockMvc.perform(
                        get("/api/v1/fresh-products/due-date?section_code=4&expires_in=15")
                                .header("Authorization", "Bearer token-errado"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Testa se um usuário consegue acessar uma rota com um token válido")
    public void itShouldAcessAuthenticatedRoute() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("agent1");
        loginDTO.setPassword("123456");

        String credentials = objectMapper.writeValueAsString(loginDTO);

        MvcResult result = mockMvc.perform(post(AUTH_URL).contentType(MediaType.APPLICATION_JSON).content(credentials)).andReturn();
        TokenDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(get("/api/v1/fresh-products/due-date?section_code=1&expires_in=15")
                        .header("Authorization", dto.getAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa se usuário é registrado")
    public void itShouldRegisterAUser() throws Exception {
        RegisterDTO register = new RegisterDTO();
        register.setUsername("usuario1");
        register.setPassword("654321");
        register.setPasswordConfirm("654321");
        register.setEmail("usuario1@mercadolibre.com");
        register.setCpf("796.929.410-35");

        String payload = objectMapper.writeValueAsString(register);

        mockMvc.perform(post(REGISTER_URL).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/me"))
                .andExpect(jsonPath("$.username").value("usuario1"))
                .andExpect(jsonPath("$.email").value("usuario1@mercadolibre.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_CUSTOMER"));
    }

    @Test
    @DisplayName("Testa se um erro é retornado ao tentar se registrar com um CPF inválido")
    public void itShouldReturnABadRequestInvalidCPF() throws Exception {
        RegisterDTO register = new RegisterDTO();
        register.setUsername("usuario1");
        register.setPassword("654321");
        register.setPasswordConfirm("654321");
        register.setEmail("usuario1@mercadolibre.com");
        register.setCpf("123.642.154-05");

        String payload = objectMapper.writeValueAsString(register);

        mockMvc.perform(post(REGISTER_URL).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF inválido!"));
    }

    @Test
    @DisplayName("Testa se um erro é retornado ao informar senhas incorretas.")
    public void itShouldReturnABadRequestIncorrectPassword() throws Exception {
        RegisterDTO register = new RegisterDTO();
        register.setUsername("usuario1");
        register.setPassword("654321");
        register.setPasswordConfirm("654331");
        register.setEmail("usuario1@mercadolibre.com");
        register.setCpf("041.278.675-31");

        String payload = objectMapper.writeValueAsString(register);

        mockMvc.perform(post(REGISTER_URL).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("A senha de confirmação incorreta!"));
    }

    @Test
    @DisplayName("Testa se com um refresh token é possível buscar outro token válido")
    public void itShouldRefreshAccessToken() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("agent1");
        loginDTO.setPassword("123456");

        String credentials = objectMapper.writeValueAsString(loginDTO);

        MvcResult result = mockMvc.perform(post(AUTH_URL).contentType(MediaType.APPLICATION_JSON).content(credentials)).andReturn();
        TokenDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(post(REFRESH_TOKEN_URL).param("token", dto.getRefreshToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.refresh_token").exists());
    }

    @Test
    @DisplayName("Testa se com um refresh token é possível buscar outro token válido")
    public void itShouldNotFoundUserInRefreshToken() throws Exception {
        mockMvc.perform(post(REFRESH_TOKEN_URL).param("token", "as8d7as8d7q8we7q8we99q8we9q"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Refresh token inválido!"));
    }

    @Test
    @DisplayName("Testa se um erro é retornado ao tentar atualizar um token sem informar o refresh token")
    public void itShouldReturnBadRequestWithoutRefreshToken() throws Exception {
        mockMvc.perform(post(REFRESH_TOKEN_URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Required request parameter 'token' for method parameter type String is not present"));
    }

    @Test
    @DisplayName("Testa se um usuário logado consegue acessar as informações de seu perfil.")
    public void itShouldGetUserLoggedProfile() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("agent1");
        loginDTO.setPassword("123456");

        String credentials = objectMapper.writeValueAsString(loginDTO);

        MvcResult result = mockMvc.perform(post(AUTH_URL).contentType(MediaType.APPLICATION_JSON).content(credentials)).andReturn();
        TokenDTO dto = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDTO.class);

        mockMvc.perform(get(PROFILE_URL).header("Authorization", dto.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("agent1"))
                .andExpect(jsonPath("$.email").value("agent1@mercadolibre.com"))
                .andExpect(jsonPath("$.roles").value(containsInAnyOrder("ROLE_CUSTOMER","ROLE_AGENT")));
    }

    @Test
    @DisplayName("Testa se um usuário consegue enviar uma requisição de reset de senha.")
    public void itShouldSendAPasswordResetRequest() throws Exception {
        PasswordResetDTO reset = new PasswordResetDTO();
        reset.setEmail("agent1@mercadolibre.com");

        String payload = objectMapper.writeValueAsString(reset);

        mockMvc.perform(post(PASSWORD_RESET_URL)
                .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Um E-mail de alteração de senha foi enviado para agent1@mercadolibre.com"));

        String token = FakeEmailServiceImpl.LAST_EMAIL_SENT.get("token");
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Testa se um usuário consegue resetar a senha.")
    public void itShouldResetAUserPassword() throws Exception {
        PasswordResetDTO reset = new PasswordResetDTO();
        reset.setEmail("agent1@mercadolibre.com");

        //Envia request de alteração de senha
        String payload = objectMapper.writeValueAsString(reset);
        mockMvc.perform(post(PASSWORD_RESET_URL)
                .contentType(MediaType.APPLICATION_JSON).content(payload)).andExpect(status().isOk());
        String token = FakeEmailServiceImpl.LAST_EMAIL_SENT.get("token");

        //Request para alterar senha
        ChangePasswordDTO passwords = new ChangePasswordDTO();
        passwords.setPassword("aaabbb");
        passwords.setPasswordConfirm("aaabbb");

        payload = objectMapper.writeValueAsString(passwords);

        mockMvc.perform(post(PASSWORD_RESET_URL + "/verify").queryParam("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Sua senha foi alterada com sucesso!"));
    }
}
