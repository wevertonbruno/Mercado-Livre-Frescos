package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA para a entidade Seller
 * @author Weverton Bruno
 */

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}
