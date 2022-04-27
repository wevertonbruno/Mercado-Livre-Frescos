package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MissingProductExceptions.class)
    public ResponseEntity<String> notNullException(MissingProductExceptions notNullExeception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notNullExeception.getMessage());
    }

    @ExceptionHandler(UnregisteredUser.class)
    public ResponseEntity<String> UnregisteredUser(UnregisteredUser notNullExeception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notNullExeception.getMessage());
    }

}
