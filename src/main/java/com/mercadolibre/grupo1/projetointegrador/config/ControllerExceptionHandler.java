package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDTO> authenticationException(AccessDeniedException e,
                                                                HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.forbidden("Acesso não autorizado!",
                        request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDTO> badCredentials(BadCredentialsException e,
                                                       HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.unauthorized(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDTO> usernamenotfound(UsernameNotFoundException e,
                                                         HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.unauthorized(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionDTO> authenticationException(ForbiddenException e,
                                                                HttpServletRequest request) {
        ExceptionDTO response =
                ExceptionDTO.forbidden(e.getMessage(),
                        request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
 }

