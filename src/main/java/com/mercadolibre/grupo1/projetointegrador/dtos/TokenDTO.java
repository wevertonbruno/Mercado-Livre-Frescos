package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    @JsonProperty("access_token")
    private String accessToken;
}
