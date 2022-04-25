package com.mercadolibre.grupo1.projetointegrador.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(NotNullExeception.class)
//    public ResponseEntity<String> notNullException(NotNullExeception notNullExeception) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notNullExeception.getMessage());
//    }

}
