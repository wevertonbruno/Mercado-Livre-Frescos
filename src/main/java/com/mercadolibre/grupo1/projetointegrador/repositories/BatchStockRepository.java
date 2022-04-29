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
            "SELECT b FROM InboundOrder i " +
                    "INNER JOIN BatchStock b ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Section s ON s.id = i.section.id " +
                    "WHERE s.id = :sectionId")
    Set<BatchStock> findStockBySectionId(Long sectionId);

    /**
     * @author Rogério Lambert
     * esta query retorna todos os lotes de um determinado produto de um determinado warehouse,
     * e com prazo de validade maior que 3 semanas
     * a query traz a ordenação default por numero de ID
     */

    @Query(value =
            "SELECT b FROM Warehouse w " +
                    "INNER JOIN Section s ON w.id = s.warehouse.id " +
                    "INNER JOIN InboundOrder i ON s.id = i.section.id " +
                    "INNER JOIN BatchStock b ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Product p ON p.id = b.product.id " +
                    "WHERE s.warehouse.id = :warehouseId AND b.product.id = :productId " +
                    "AND b.dueDate > CURRENT_DATE + 21")
    Set<BatchStock> findStockByProductIdAndWarehouseId(Long productId, Long warehouseId);

    /**
     * @author Rogério Lambert
     * esta query retorna todos os lotes de um determinado produto de um determinado warehouse
     * e com prazo de validade maior que 3 semanas
     * a query traz a ordenação crescente por data de validade
     */

    @Query(value =
            "SELECT b FROM Warehouse w " +
                    "INNER JOIN Section s ON w.id = s.warehouse.id " +
                    "INNER JOIN InboundOrder i ON s.id = i.section.id " +
                    "INNER JOIN BatchStock b ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Product p ON p.id = b.product.id " +
                    "WHERE s.warehouse.id = :warehouseId AND b.product.id = :productId " +
                    "AND b.dueDate > CURRENT_DATE + 21" +
                    "ORDER BY b.dueDate ASC" )
    Set<BatchStock> findStockByProductIdAndWarehouseIdOrderByDueDate(Long productId, Long warehouseId);

    /**
     * @author Rogério Lambert
     * esta query retorna todos os lotes de um determinado produto de um determinado warehouse
     * e com prazo de validade maior que 3 semanas
     * a query traz a ordenação crescete por quantidade disponível
     */

    @Query(value =
            "SELECT b FROM Warehouse w " +
                    "INNER JOIN Section s ON w.id = s.warehouse.id " +
                    "INNER JOIN InboundOrder i ON s.id = i.section.id " +
                    "INNER JOIN BatchStock b ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Product p ON p.id = b.product.id " +
                    "WHERE s.warehouse.id = :warehouseId AND b.product.id = :productId " +
                    "AND b.dueDate > CURRENT_DATE + 21" +
                    "ORDER BY b.currentQuantity ASC" )
    Set<BatchStock> findStockByProductIdAndWarehouseIdOrderByCurrentQuantity(Long productId, Long warehouseId);
}
