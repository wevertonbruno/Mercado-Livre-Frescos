package com.mercadolibre.grupo1.projetointegrador.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.grupo1.projetointegrador.anotations.validation.CPF;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDTO {
    @NotNull(message = "O nome de usuário é obrigatório!")
    private String username;
    @NotNull(message = "A senha é obrigatória!")
    private String password;
    @NotNull(message = "A senha de confirmação é obrigatória!")
    @JsonProperty("password_confirm")
    private String passwordConfirm;
    @NotNull(message = "O E-mail é obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;
    @NotNull(message = "O CPF é obrigatório!")
    @CPF(message = "CPF inválido!")
    private String cpf;
}
