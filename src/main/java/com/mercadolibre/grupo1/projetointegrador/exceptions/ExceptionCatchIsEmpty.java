package com.mercadolibre.grupo1.projetointegrador.exceptions;
/*
@author Gabriel Essenio
Cria exceçao caso lista esteja vazia
 */
public class ExceptionCatchIsEmpty extends RuntimeException {
        public ExceptionCatchIsEmpty(String message) {
            super(message);
        }
}
