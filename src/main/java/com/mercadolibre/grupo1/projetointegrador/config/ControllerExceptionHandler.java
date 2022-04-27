package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredProducts;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MissingProductExceptions.class)
    public ResponseEntity<String> notNullException(MissingProductExceptions missingProductExceptions) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(missingProductExceptions.getMessage());
    }

    @ExceptionHandler(UnregisteredUser.class)
    public ResponseEntity<String> unregisteredUser(UnregisteredUser unregisteredUser) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unregisteredUser.getMessage());
    }

    @ExceptionHandler(UnregisteredProducts.class)
    private ResponseEntity<String> unregisteredProduct(UnregisteredProducts unregisteredProducts) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unregisteredProducts.getMessage());
    }

}
