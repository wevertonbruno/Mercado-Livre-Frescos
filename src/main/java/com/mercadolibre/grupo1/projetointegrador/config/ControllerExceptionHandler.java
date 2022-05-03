package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.exceptions.*;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredProducts;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import org.springframework.http.HttpStatus;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
/**
 * @author  Nayara Coca, Gabriel Essenio
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


    @ExceptionHandler(OvercapacityException.class)
    public ResponseEntity<ExceptionDTO> excededCapacityException(OvercapacityException e,
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
    @ExceptionHandler(ProductNotAvailable.class)
    public ResponseEntity<ExceptionDTO> productNotAvailable(ProductNotAvailable e,
                                                                 HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.notFound(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDTO> missingParametersException(MissingServletRequestParameterException e,
                                                                 HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.badRequest(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ExceptionDTO> missingParametersException(InvalidOperationException e,
                                                                   HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.badRequest(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    //exceçao que trato caso a lista esteja vazia

    @ExceptionHandler(ListIsEmptyException.class)
    public ResponseEntity<ExceptionDTO> listEmptyException (ListIsEmptyException e, HttpServletRequest request){
        ExceptionDTO response = ExceptionDTO.notFound(e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
 }

