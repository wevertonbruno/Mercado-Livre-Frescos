package com.mercadolibre.grupo1.projetointegrador.services.mappers;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BatchStockMapper {

    public BatchStock toBatchStock(BatchStockDTO batchStockDTO){
        BatchStock batchStock = new BatchStock();

        batchStock.setCurrentTemperature(batchStockDTO.getCurrentTemperature());
        batchStock.setMinimumTemperature(batchStockDTO.getMinimumTemperature());

        batchStock.setInitialQuantity(batchStockDTO.getInitialQuantity());
        batchStock.setCurrentQuantity(batchStockDTO.getCurrentQuantity());

        batchStock.setManufacturingDateTime(batchStockDTO.getManufacturingDateTime());
        batchStock.setDueDate(batchStockDTO.getDueDate());

        return batchStock;
    }
}
