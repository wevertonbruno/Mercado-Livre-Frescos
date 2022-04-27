package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entidade que mapeia a tabela de Representantes (agents).
 * @author Weverton Bruno
 */

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "agents")
public class Agent extends AuthenticableUser{
}
