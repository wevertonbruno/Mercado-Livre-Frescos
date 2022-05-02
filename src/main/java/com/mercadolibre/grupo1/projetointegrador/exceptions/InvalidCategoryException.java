package com.mercadolibre.grupo1.projetointegrador.exceptions;

/**
 * Excecao para categoria invalida
 * @author Weverton Bruno
 */
public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message) {
        super(message);
    }
}
