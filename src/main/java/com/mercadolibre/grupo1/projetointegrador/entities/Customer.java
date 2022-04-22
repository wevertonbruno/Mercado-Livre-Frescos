package com.mercadolibre.grupo1.projetointegrador.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * classe responsavel por registrar os informacoes de um cliente (comprador)
 * @Author: Rogerio Lambert
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AuthenticableUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    @OneToMany
    private List<PurchaseOrder> orders;
}
