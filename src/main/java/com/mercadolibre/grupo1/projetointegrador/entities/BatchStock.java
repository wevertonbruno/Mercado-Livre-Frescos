package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Nayara Coca
 * Criação da entidade Batch Stock
 * Gerando getters e setters
 */

@Entity
@Table(name = "batch_stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    @ManyToOne
    @JoinColumn(name = "inbound_order_id", referencedColumnName = "id")
    private InboundOrder inboundOrder;

    public Double getVolume() {
        return product.getVolume() * currentQuantity;
    }
}
