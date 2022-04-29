package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rogério Lambert
 * sevice responsável por manipular informações de armazéns
 */

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    /**
     * @author Rogério Lambert
     * metodo busca armazém por id, e lança exceção caso não encontre
     */

    public Warehouse findById(long warehouseId) {
        String errorMessage = "A warehouse com ID " + warehouseId + " não está cadastrada";
        return warehouseRepository
                .findById(warehouseId)
                .orElseThrow(() ->
                        new EntityNotFoundException(errorMessage));
    }
}
