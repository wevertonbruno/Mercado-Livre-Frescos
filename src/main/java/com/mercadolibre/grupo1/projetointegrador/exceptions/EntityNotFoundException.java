package com.mercadolibre.grupo1.projetointegrador.exceptions;

/**
 * Excecao para Entidade nao encontrada
 * @author Weverton Bruno
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
