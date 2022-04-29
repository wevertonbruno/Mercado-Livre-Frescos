package com.mercadolibre.grupo1.projetointegrador.exceptions;

public class ProductNotAvailable extends RuntimeException {
    public ProductNotAvailable(String message) {
        super(message);
    }
}
