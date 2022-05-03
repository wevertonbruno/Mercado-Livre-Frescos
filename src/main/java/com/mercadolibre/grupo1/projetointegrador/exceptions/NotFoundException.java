package com.mercadolibre.grupo1.projetointegrador.exceptions;

/**
 * @author Nayara Coca
 * lança mensagem de excessão de produto não encontrado
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
