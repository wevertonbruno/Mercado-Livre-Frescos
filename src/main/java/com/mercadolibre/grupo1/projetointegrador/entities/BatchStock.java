package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Nayara Coca
 * Criação da entidade Batch Stock
 * Gerando getters e setters
 */

@Entity
@Builder
@Table(name = "batch_stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BatchStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Float currentTemperature;
    private Float minimumTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDateTime manufacturingDateTime;
    private LocalDate dueDate;
//    @ManyToOne
//    @JoinColumn(name = "inbound_order_id")
//    private InboundOrder inboundOrder;

    @ManyToOne
    @JoinColumn(name = "inbound_order_id", referencedColumnName = "id")
    private InboundOrder inboundOrder;

    public Double getVolume() {
        return product.getVolume() * currentQuantity;
    }
}
