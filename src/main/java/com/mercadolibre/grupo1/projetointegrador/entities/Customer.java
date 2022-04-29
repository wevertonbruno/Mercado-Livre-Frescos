package com.mercadolibre.grupo1.projetointegrador.entities;

import java.util.List;

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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends AuthenticableUser {
    private String cpf;
    @OneToMany(mappedBy = "customer")
    private List<PurchaseOrder> orders;
}
