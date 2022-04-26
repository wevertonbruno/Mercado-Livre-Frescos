package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.dtos.ExceptionDTO;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

 }
