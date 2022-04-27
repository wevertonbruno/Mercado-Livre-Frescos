package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
/*
* @author Gabriel Essenio
* Classe de Controller Advice que trata o retorno das exceçao
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    /*
    @author Gabriel Essenio
    exceçao que trato caso a lista esteja vazia
     */
    @ExceptionHandler(ListIsEmptyException.class)
    public ResponseEntity<ExceptionDTO> listEmptyException (ListIsEmptyException e, HttpServletRequest request){
        ExceptionDTO response = ExceptionDTO.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    /*
    @author Gabriel Essenio
    exceçao que trata caso o status de category nao seja um dos Enums
     */
    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<ExceptionDTO> statusCategoryException (InvalidCategoryException e, HttpServletRequest request){
        ExceptionDTO response = ExceptionDTO.badRequest(e.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }
}
