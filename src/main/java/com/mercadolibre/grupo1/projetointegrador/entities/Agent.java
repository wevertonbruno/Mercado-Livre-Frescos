package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade que mapeia a tabela de Representantes (agents).
 * @author Weverton Bruno
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "agents")
public class Agent extends AuthenticableUser{
    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;
    public Agent(AuthenticableUser user, Warehouse warehouse) {
        super.setId(user.getId());
        super.setEmail(user.getEmail());
        super.setUsername(user.getUsername());
        super.setPassword(user.getUsername());
        super.setRoles(user.getRoles());
        this.warehouse = warehouse;
    }
}
