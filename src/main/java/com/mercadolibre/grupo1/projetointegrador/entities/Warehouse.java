package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Entidade responsável pelo Warehouse
// @author Ederson Rodrigues Araujo

@Entity
@Getter @Setter
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String name;

}
