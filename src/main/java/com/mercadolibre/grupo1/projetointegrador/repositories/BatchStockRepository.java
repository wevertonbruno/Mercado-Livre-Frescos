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
}
