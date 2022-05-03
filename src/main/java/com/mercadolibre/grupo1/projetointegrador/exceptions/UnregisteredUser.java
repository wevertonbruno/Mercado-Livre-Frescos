package com.mercadolibre.grupo1.projetointegrador.exceptions;

public class UnregisteredUser extends RuntimeException{
    public UnregisteredUser(String message) {
        super(message);
    }
}
