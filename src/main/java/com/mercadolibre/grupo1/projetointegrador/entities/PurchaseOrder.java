package com.mercadolibre.grupo1.projetointegrador.entities;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * classe responsavel por registrar os informacoes de uma compra do cliente, sendo utilizada
 * desde o momento que o cliente abre o carrinho de compra (com StatusOrder de "OPENED") até
 * o fechamento da depois que o produto é entregue
 *
 * @Author: Rogerio Lambert
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // a anotacao FetchType.EAGER ira garantir o carregamento de todos os dados de products
    private List<PurchaseItem> products;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public BigDecimal totalPrice() {
        return products.stream().reduce(BigDecimal.valueOf(0), (total, item) -> total.add(item.subTotal()), BigDecimal::add);
    }
}
