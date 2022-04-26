package com.mercadolibre.grupo1.projetointegrador.exceptions;
/*
@author Gabriel Essenio
Cria exceçao caso o status de category nao esteja correto
 */
public class ExceptionCatchStatusCategory extends RuntimeException{
    public ExceptionCatchStatusCategory(String message) {
        super(message);
    }
}
