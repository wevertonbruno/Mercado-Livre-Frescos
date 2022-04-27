package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nayara Coca
 * Criação repositório de product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
