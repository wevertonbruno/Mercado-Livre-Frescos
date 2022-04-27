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

    /**
     *
     * @author Rogério Lambert
     * esta query retorna todos os lotes de um determinado produto de um determinado warehouse
     */

    @Query(value =
            "SELECT b.* FROM warehouses w " +
                    "INNER JOIN batch_stocks b ON b.id = ib.batch_stock_id " +
                    "INNER JOIN inbound_orders_batch_stock ib ON i.id = ib.inbound_order_id " +
                    "INNER JOIN inbound_orders i ON s.id = i.section_id " +
                    "INNER JOIN sections s ON w.id = s.warehouse_id " +
                    "INNER JOIN products p ON p.id = b.product_id " +
                    "WHERE s.warehouse_id = :warehouseId AND b.product_id = :productId ",
            nativeQuery = true)
    Set<BatchStock> findStockByProductIdAndWarehouseId(Long producId, Long warehouseId);
}
