package com.mercadolibre.grupo1.projetointegrador.exceptions;

/**
 * Excecao para limite de capacidade excedido
 * @author Weverton Bruno
 */
public class OvercapacityException extends RuntimeException {
    public OvercapacityException(String message) {
        super(message);
    }
}
