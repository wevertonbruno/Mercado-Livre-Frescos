package com.mercadolibre.grupo1.projetointegrador.exceptions;
/*
@author Gabriel Essenio
Cria exceçao caso lista esteja vazia
 */
public class ListIsEmptyException extends RuntimeException {
        public ListIsEmptyException(String message) {
            super(message);
        }
}
