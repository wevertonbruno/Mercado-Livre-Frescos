package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Entidade que mapeia a tabela de vendedores (sellers).
 * @author Weverton Bruno
 */

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "sellers")
public class Seller extends AuthenticableUser{
    @OneToMany(mappedBy = "seller")
    private Set<Product> products;
}
