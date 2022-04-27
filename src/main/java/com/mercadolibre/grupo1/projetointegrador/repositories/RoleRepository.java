package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA para a entidade Role
 * @author Weverton Bruno
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
