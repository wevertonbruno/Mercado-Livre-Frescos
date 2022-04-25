package com.mercadolibre.grupo1.projetointegrador.entities.enums;
/**
 * Este enun registra do em que situacao a pedido está, sendo que enquanto estiver no carrinho
 * de compra o status é "OPENED" habilitando atualizacoes nas informacoes, os demais status
 * nao é possivel fazer atualizacoes.
 * @Author: Rogerio Lambert
 */

public enum OrderStatus {
    OPENED,
    PREPARING,
    SENT,
    CLOSED
}
