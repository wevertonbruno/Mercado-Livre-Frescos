package com.mercadolibre.grupo1.projetointegrador.entities;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ederson Rodrigues Araujo
 * Entidade responsável pela Section
 */

@Builder
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private Double capacity;

    // A sessão tera um relacionamento de muito para um com a Warehouse.
    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "section")
    private Set<InboundOrder> inboundOrders;

    public Set<BatchStock> getStock(){
        Set<BatchStock> stock = new HashSet<>();

        for (InboundOrder order: inboundOrders){
            stock.addAll(order.getBatchStock());
        }

        return stock;
    }

    public Double getRemainingCapacity(){
        return capacity - getStock().stream().reduce(0.0, (total, batchItem) -> total + batchItem.getVolume(), Double::sum);
    }
}
