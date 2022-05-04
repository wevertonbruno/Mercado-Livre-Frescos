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
public class Customer extends AuthenticableUser {
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<PurchaseOrder> orders = new ArrayList<>();

    public Customer(AuthenticableUser user, String cpf){
        super(user);
        this.cpf = cpf;
    }
}
