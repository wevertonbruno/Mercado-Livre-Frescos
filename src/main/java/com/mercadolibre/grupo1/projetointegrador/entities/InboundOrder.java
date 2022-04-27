package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Nayara Coca
 * Criação da entidade Inbound Order
 * Gerando getters e setters
 */

@Entity
@Builder
@Table(name = "inbound_orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;
    @OneToMany(mappedBy = "inboundOrder")
    private List<BatchStock> batchStock;
}
