package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * @author Ederson Rodrigues Araujo
 * Entidade responsável pela Section
 */

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByWarehouse(Long id);
    Optional<Section> findByIdAndWarehouse_Id(Long sectionId, Long warehouseId);
}
