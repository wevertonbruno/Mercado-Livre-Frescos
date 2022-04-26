package com.mercadolibre.grupo1.projetointegrador.exceptions;

public class ExcededCapacityException extends RuntimeException {
    public ExcededCapacityException(String message) {
        super(message);
    }
}
