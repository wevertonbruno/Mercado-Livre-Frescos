package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredProducts;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MissingProductExceptions.class)
    public ResponseEntity<ExceptionDTO> notNullException(MissingProductExceptions e, HttpServletRequest request) {
        ExceptionDTO response = ExceptionDTO.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnregisteredUser.class)
    public ResponseEntity<ExceptionDTO> unregisteredUser(UnregisteredUser e, HttpServletRequest request) {
        ExceptionDTO response = ExceptionDTO.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnregisteredProducts.class)
    private ResponseEntity<ExceptionDTO> unregisteredProduct(UnregisteredProducts e, HttpServletRequest request) {
        ExceptionDTO response = ExceptionDTO.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
