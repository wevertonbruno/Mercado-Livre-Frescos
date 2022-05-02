package com.mercadolibre.grupo1.projetointegrador.exceptions;

public class UnregisteredProducts extends RuntimeException{
    public UnregisteredProducts(String message) {
        super(message);
    }
}
