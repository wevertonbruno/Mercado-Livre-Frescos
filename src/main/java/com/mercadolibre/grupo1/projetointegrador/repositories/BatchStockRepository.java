package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Nayara Coca
 * Criação repositório de batch stock
 */
@Repository
public interface BatchStockRepository extends JpaRepository<BatchStock,Long> {
    @Query(value =
            "SELECT b.* FROM inbound_orders i " +
                "INNER JOIN inbound_orders_batch_stock ib ON i.id = ib.inbound_order_id " +
                "INNER JOIN batch_stocks b ON b.id = ib.batch_stock_id " +
                "INNER JOIN sections s ON s.id = i.section_id " +
                "INNER JOIN products p ON p.id = b.product_id " +
            "WHERE i.section_id = :sectionId",
            nativeQuery = true)
    Set<BatchStock> findStockBySectionId(Long sectionId);

    @Query(value =
            "SELECT b.* FROM inbound_orders i " +
                    "INNER JOIN inbound_orders_batch_stock ib ON i.id = ib.inbound_order_id " +
                    "INNER JOIN batch_stocks b ON b.id = ib.batch_stock_id " +
                    "INNER JOIN sections s ON s.id = i.section_id " +
                    "INNER JOIN products p ON p.id = b.product_id " +
                    "WHERE b.product_id = :productId",
            nativeQuery = true)
    List<BatchStock> findStockByProductId(Long productId);
}
