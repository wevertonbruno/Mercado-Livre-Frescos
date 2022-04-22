package com.mercadolibre.grupo1.projetointegrador.entities;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@SuperBuilder
@NoArgsConstructor
public class Agent extends AuthenticableUser{
}
