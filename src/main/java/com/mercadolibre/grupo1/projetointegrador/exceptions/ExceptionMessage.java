package com.mercadolibre.grupo1.projetointegrador.exceptions;

/**
 * @author Nayara Coca
 * lança mensagem de excessão
 */
public class ExceptionMessage extends RuntimeException {
    public ExceptionMessage(String message) {
        super(message);
    }
}
