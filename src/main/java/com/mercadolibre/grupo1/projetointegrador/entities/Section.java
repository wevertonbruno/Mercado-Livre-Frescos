package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// Entidade responsável pela Section
// @author Ederson Rodrigues Araujo

@Entity
@Getter @Setter
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double capacity;

    // A sessão tera um relacionamento de muito para um com a Warehouse.
    @ManyToOne
    private Warehouse warehouse;
}
