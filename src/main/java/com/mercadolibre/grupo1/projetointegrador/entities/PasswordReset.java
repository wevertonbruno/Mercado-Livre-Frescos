package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "password_reset_tokens")
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AuthenticableUser user;
    @Column(nullable = false, unique = true)
    private String token;
}
