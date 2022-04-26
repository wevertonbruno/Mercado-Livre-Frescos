package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchStockService {
    private final ProductService productService;

    public BatchStock createFromDTO(BatchStockDTO batchStockDTO) {
        BatchStock batchStock = new BatchStock();
        batchStock.setProduct(productService.findById(batchStockDTO.getProductId()));

        batchStock.setCurrentTemperature(batchStockDTO.getCurrentTemperature());
        batchStock.setMinimumTemperature(batchStockDTO.getMinimumTemperature());

        batchStock.setInitialQuantity(batchStockDTO.getInitialQuantity());
        batchStock.setCurrentQuantity(batchStockDTO.getCurrentQuantity());

        batchStock.setManufacturingDateTime(batchStockDTO.getManufacturingDateTime());
        batchStock.setDueDate(batchStockDTO.getDueDate());

        return batchStock;
    }
}
