package com.mercadolibre.grupo1.projetointegrador.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.*;

import javax.persistence.*;

/**
 * @author Ederson Rodrigues Araujo
 * Entidade responsável pela Section
 */

@Entity
@Builder
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
    @JsonIgnore
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;
}
