package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Nayara Coca
 * Criação repositório de inbound order
 */
public interface InboundOrderRepository extends JpaRepository<InboundOrder,Long> {
    List<InboundOrder> findBySection_Id(Long sectionId);
}
