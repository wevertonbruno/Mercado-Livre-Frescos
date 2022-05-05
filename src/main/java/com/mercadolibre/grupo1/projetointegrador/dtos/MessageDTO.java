package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    private String message;
    public static MessageDTO of(String value){ return new MessageDTO(value); }
}
