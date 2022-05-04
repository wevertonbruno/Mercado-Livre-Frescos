package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que mapeia a tabela de vendedores (sellers).
 * @author Weverton Bruno
 */

@Entity
@NoArgsConstructor
@Table(name = "sellers")
public class Seller{

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private AuthenticableUser user;

    @OneToMany(mappedBy = "seller")
    private Set<Product> products = new HashSet<>();

    public Seller(AuthenticableUser user){
        this.user = user;
    }
}
