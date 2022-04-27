package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * classe responsavel por acionar a funcionalidades de gestao do banco de dados de cliente
 * @Author: Rogerio Lambert
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
