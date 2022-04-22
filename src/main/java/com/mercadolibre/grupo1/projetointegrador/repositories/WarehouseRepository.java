package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositório da Warehouse.

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
