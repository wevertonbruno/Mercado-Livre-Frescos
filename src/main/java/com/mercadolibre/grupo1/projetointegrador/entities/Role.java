package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Entidade que mapeia a tabela de cargos para autorizacao (roles).
 * @author Weverton Bruno
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_AGENT = "ROLE_AGENT";
    public static final String ROLE_SELLER = "ROLE_SELLER";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
