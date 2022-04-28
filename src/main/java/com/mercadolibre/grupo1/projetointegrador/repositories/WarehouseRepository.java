package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

// Repositório da Warehouse.
// @author Ederson Rodrigues Araujo

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value =
    "select w from Warehouse w inner join Section s on w.id = s.warehouse.id " +
            "inner join InboundOrder i on s.id = i.section.id " +
            "inner join BatchStock b on i.id = b.inboundOrder.id " +
            "inner join Product p on p.id = b.product.id where p.id = :productsId")
    List<Warehouse> findProductsInWarehouse(long productsId);
}
