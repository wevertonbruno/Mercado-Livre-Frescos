package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionNotFound {
    private Integer status;
    private String message;
    private Instant timestamp;
    private String path;
    public static ExceptionNotFound notFound(String message, String path) {
        return new ExceptionNotFound(HttpStatus.NOT_FOUND.value(), message, Instant.now(), path);
    }
}
