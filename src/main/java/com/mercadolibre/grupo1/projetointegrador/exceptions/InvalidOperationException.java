package com.mercadolibre.grupo1.projetointegrador.exceptions;

/**
 * Excecao para operacao invalida
 * @author Weverton Bruno
 */
public class InvalidOperationException extends RuntimeException{
    public InvalidOperationException(String message) {
        super(message);
    }
}
