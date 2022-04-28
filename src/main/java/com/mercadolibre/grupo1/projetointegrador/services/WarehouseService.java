package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> findWarehouse(Long productsId) {
        List<Warehouse> stockByWarehouseProduct = warehouseRepository.findProductsInWarehouse(productsId);
        if(stockByWarehouseProduct.isEmpty()){
            throw new NotFoundException("PRODUTO NÃO ENCONTRADO");
        }
        return stockByWarehouseProduct;

    }

}

