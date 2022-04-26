package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ederson Rodrigues Araujo
 * Entidade responsável pela Section
 */

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
    private Double capacity;

    // A section tera um relacionamento de manyToOne com a Warehouse.
    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    // A section tera um relacionamento de oneToMany com a InboundOrder.
    @OneToMany(mappedBy = "section")
    private Set<InboundOrder> inboundOrder;
}
