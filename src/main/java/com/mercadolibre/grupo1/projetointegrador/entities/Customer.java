package com.mercadolibre.grupo1.projetointegrador.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * classe responsavel por registrar os informacoes de um cliente (comprador)
 * @Author: Rogerio Lambert
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer{
    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private AuthenticableUser user;
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<PurchaseOrder> orders = new ArrayList<>();

    public Customer(AuthenticableUser user, String cpf){
        this.user = user;
        this.cpf = cpf;
    }
}
