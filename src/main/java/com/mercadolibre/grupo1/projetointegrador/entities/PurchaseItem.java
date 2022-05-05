package com.mercadolibre.grupo1.projetointegrador.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "purchase_items")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id", referencedColumnName = "id")
    @JsonIgnore // esta anotacao garante que nao havera looping de dados quando for requisitado
    private PurchaseOrder purchaseOrder;

    private Integer quantity;

    public BigDecimal subTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
