package com.mercadolibre.grupo1.projetointegrador.dtos.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetDTO {
    @NotBlank(message = "O E-mail é obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;
}
