package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

    @JsonProperty("access_token")
    private String accessToken;
}
