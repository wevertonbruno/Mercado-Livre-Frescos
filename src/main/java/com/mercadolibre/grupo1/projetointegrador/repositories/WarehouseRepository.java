package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

/** Repositório da Warehouse
 * @author Ederson Rodrigues Araujo
 */

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value =
    "select new com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseProductDTO(w.id, sum(b.currentQuantity)) " +
            "from Warehouse w inner join Section s on w.id = s.warehouse.id " +
            "inner join InboundOrder i on s.id = i.section.id " +
            "inner join BatchStock b on i.id = b.inboundOrder.id " +
            "inner join Product p on p.id = b.product.id where p.id = :productsId " +
            "group by p")
    List<WarehouseProductDTO> findProductsInWarehouse(long productsId);
    Optional<Warehouse> findById(Long id);
}
