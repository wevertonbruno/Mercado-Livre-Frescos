package com.mercadolibre.grupo1.projetointegrador.exceptions;
/*
@author Gabriel Essenio
Cria exceçao caso o status de category nao esteja correto
 */
public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String message) {
        super(message);
    }
}
