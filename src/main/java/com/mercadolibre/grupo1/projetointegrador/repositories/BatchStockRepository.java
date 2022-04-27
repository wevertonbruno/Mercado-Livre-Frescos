package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Nayara Coca
 * Criação repositório de batch stock
 */
@Repository
public interface BatchStockRepository extends JpaRepository<BatchStock,Long> {

    /**
     * @author Ederson Rodrigues Araujo
     * filtra todos um tipo de produto com data de validade superior a 21 dias
     */
    @Query("select b from BatchStock b where b.product.id = :id and DATEDIFF('DAY', now() , b.dueDate) > 22")
    public List<BatchStock> findValidDateItems(Long id);
}
