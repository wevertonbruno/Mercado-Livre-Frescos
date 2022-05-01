package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    public Agent(AuthenticableUser user) {
        super.setId(user.getId());
        super.setEmail(user.getEmail());
        super.setUsername(user.getUsername());
        super.setPassword(user.getUsername());
        super.setRoles(user.getRoles());
    }
}
