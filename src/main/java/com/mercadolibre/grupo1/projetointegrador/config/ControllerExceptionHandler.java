package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import org.springframework.http.HttpStatus;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExcededCapacityException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
/*
* @author Gabriel Essenio
* Classe de Controller Advice que trata o retorno das exceçao
 */

import java.util.Objects;
/**
 * @author Nayara Coca
 * controladores de excessão
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> argumentNotValid(MethodArgumentNotValidException e,
                                                         HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.badRequest(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(),
                        request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ExceptionMessage.class)
    public ResponseEntity<ExceptionDTO> exceptionDTOMessage(ExceptionMessage e,
                                                            HttpServletRequest request) {
        ExceptionDTO response = ExceptionDTO.badRequest(e.getMessage(), request.getRequestURI());
      return ResponseEntity.badRequest().body(response);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> entityNotFound(EntityNotFoundException e,
                                                         HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.badRequest(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ExcededCapacityException.class)
    public ResponseEntity<ExceptionDTO> excededCapacityException(ExcededCapacityException e,
                                                       HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.badRequest(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<ExceptionDTO> invalidCategoryException(InvalidCategoryException e,
                                                                 HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.badRequest(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    /*
    @author Gabriel Essenio
    exceçao que trato caso a lista esteja vazia
     */
    @ExceptionHandler(ListIsEmptyException.class)
    public ResponseEntity<ExceptionDTO> listEmptyException (ListIsEmptyException e, HttpServletRequest request){
        ExceptionDTO response = ExceptionDTO.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

 }