package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ederson Rodrigues Araujo
 * Entidade responsável pelo Warehouse
 */


@Builder
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String name;

    @OneToMany(mappedBy = "warehouse")
    private Set<Section> sections;
}
