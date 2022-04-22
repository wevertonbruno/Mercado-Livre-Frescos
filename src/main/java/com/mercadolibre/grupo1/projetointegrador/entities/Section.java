package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double capacity;

    @ManyToOne
    private Warehouse warehouse;
}
