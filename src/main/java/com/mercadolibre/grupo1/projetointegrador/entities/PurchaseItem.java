package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * classe responsavel por registrar os informacoes de um item dentro do compra do cliente
 * @Author: Rogerio Lambert
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private PurchaseOrder purchaseOrder;
    private Integer quantity;

    public BigDecimal subTotal() {
        return product.getPrice() * BigDecimal.valueOf(quantity);
    }
}
