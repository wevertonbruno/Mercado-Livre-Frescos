package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA para a entidade Agent
 * @author Weverton Bruno
 */

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
}
