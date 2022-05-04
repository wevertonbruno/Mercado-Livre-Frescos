package com.mercadolibre.grupo1.projetointegrador.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "A senha é obrigatória!")
    private String password;
    @NotBlank(message = "A senha de confirmação é obrigatória!")
    @JsonProperty("password_confirm")
    private String passwordConfirm;
}
