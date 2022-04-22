package com.mercadolibre.grupo1.projetointegrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositório da Section

@Repository
public interface Section extends JpaRepository<Section, Long> {
}
