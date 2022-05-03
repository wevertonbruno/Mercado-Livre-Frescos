package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;

/**
 * @author Nayara Coca
 * responsável por mandar mensagem de erro se produto não for encontrado
 */

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<WarehouseProductDTO> findWarehouse(Long productsId) {
        List<WarehouseProductDTO> stockByWarehouseProduct = warehouseRepository.findProductsInWarehouse(productsId);
        if(stockByWarehouseProduct.isEmpty()){
            throw new NotFoundException("PRODUTO NÃO ENCONTRADO");
        }
        return stockByWarehouseProduct;

    }

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
