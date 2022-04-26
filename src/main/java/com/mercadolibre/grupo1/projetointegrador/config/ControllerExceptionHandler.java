package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionNotFound;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExceptionCatchIsEmpty;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExceptionCatchStatusCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
/*
@author Gabriel Essenio
Classe de Controller Advice que trata o retorno das exceçao
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    /*
    @author Gabriel Essenio
    exceçao que trato caso a lista esteja vazia
     */
    @ExceptionHandler(ExceptionCatchIsEmpty.class)
    public ResponseEntity<ExceptionNotFound> listEmptyException (ExceptionCatchIsEmpty e, HttpServletRequest request){
        ExceptionNotFound response = ExceptionNotFound.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    /*
    @author Gabriel Essenio
    exceçao que trata caso o status de category nao seja um dos Enums
     */
    @ExceptionHandler(ExceptionCatchStatusCategory.class)
    public ResponseEntity<ExceptionDTO> statusCategoryException (ExceptionCatchStatusCategory e, HttpServletRequest request){
        ExceptionDTO response = ExceptionDTO.badRequest(e.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }
}
