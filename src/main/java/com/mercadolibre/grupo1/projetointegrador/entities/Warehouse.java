package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ederson Rodrigues Araujo
 * Entidade responsável pelo Warehouse
 */

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
