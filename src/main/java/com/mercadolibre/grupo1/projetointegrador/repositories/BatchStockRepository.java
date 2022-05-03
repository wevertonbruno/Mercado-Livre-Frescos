package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

    @Repository
    public interface BatchStockRepository extends JpaRepository<BatchStock,Long> {
    /**
     * Listagem do estoque de lotes da sessao
     * @param sectionId Id da sessao de onde se quer buscar o lote
     * @author Weverton Bruno
     */
    @Query(value =
            "SELECT b FROM InboundOrder i " +
                    "INNER JOIN BatchStock b ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Section s ON s.id = i.section.id " +
                    "WHERE s.id = :sectionId")
    Set<BatchStock> findStockBySectionId(Long sectionId);

    /**
     * Listagem do o estoque de lotes da sessao filtrados por dias de validade
     * @param sectionId Id da sessao de onde se quer buscar o lote
     * @param start data inicial para filtro de produtos vencidos
     * @param end data final para filtro de produtos vencidos
     * @author Weverton Bruno
     */
    @Query(value =
            "SELECT b FROM InboundOrder i " +
                    "INNER JOIN BatchStock b ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Section s ON s.id = i.section.id " +
                    "WHERE s.id = :sectionId AND b.dueDate BETWEEN :start AND :end " +
                    "ORDER BY b.dueDate ASC")
    List<BatchStock> findStockBySectionIdAndDueDateBetween(Long sectionId, LocalDate start, LocalDate end);

    /**
     * Listagem do o estoque de lotes de todos os warehouses
     * @param warehouseId Id do armazem
     * @param category categoria do produto
     * @param start data inicial para filtro de produtos vencidos
     * @param end data final para filtro de produtos vencidos
     * @param sort parametro de ordenacao
     * @author Weverton Bruno
     */
    @Query(value =
            "SELECT b FROM BatchStock b " +
                    "INNER JOIN InboundOrder i ON b.inboundOrder.id = i.id " +
                    "INNER JOIN Product p ON p.id = b.product.id " +
                    "INNER JOIN Section s ON s.id = i.section.id " +
                    "INNER JOIN Warehouse w ON w.id = s.warehouse.id " +
                    "WHERE b.dueDate BETWEEN :start AND :end AND p.category = :category AND w.id = :warehouseId")
    List<BatchStock> findWarehouseStockByCategoryAndDueDateBetween(Long warehouseId, ProductCategory category, LocalDate start, LocalDate end, Sort sort);


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

    /**
     * @author Ederson Rodrigues Araujo
     * filtra todos um tipo de produto com data de validade superior a 21 dias
     */
    @Query("select sum(b.currentQuantity) from BatchStock b WHERE b.product.id = :id and DATEDIFF('DAY', now() , b.dueDate) > 21 group by b.product.id")
    public Double findValidDateItems(Long id);


    @Query(value =
            "SELECT b.* FROM inbound_orders i " +
                    "INNER JOIN batch_stocks b ON b.inbound_order_id = i.id " +
                    "INNER JOIN sections s ON s.id = i.section_id " +
                    "INNER JOIN products p ON p.id = b.product_id " +
                    "WHERE b.product_id = :productId",
            nativeQuery = true)
    List<BatchStock> findStockByProductId(Long productId);
}
