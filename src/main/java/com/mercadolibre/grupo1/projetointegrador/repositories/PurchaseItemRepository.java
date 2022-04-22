package com.mercadolibre.grupo1.projetointegrador.repositories;


import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * classe responsavel por acionar a funcionalidades de gestao do banco de dados de itens de compra
 * @Author: Rogerio Lambert
 */

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
