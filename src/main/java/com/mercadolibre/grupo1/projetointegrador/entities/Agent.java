package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Agent{

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private AuthenticableUser user;
    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;
    public Agent(AuthenticableUser user, Warehouse warehouse) {
        this.user = user;
        this.warehouse = warehouse;
    }
}
