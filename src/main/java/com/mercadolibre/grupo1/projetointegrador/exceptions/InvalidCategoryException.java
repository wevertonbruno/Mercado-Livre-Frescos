package com.mercadolibre.grupo1.projetointegrador.exceptions;
/*
@author Gabriel Essenio
Cria exceçao caso o status de category nao esteja correto
 */

/**
 * Excecao para categoria invalida
 * @author Weverton Bruno
 */
public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message) {
        super(message);
    }
}
