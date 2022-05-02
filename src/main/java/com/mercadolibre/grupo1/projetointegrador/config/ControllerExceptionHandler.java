package com.mercadolibre.grupo1.projetointegrador.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
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
                ExceptionDTO.unauthorized("Usuário e/ou senha inválidos!",
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
 }
