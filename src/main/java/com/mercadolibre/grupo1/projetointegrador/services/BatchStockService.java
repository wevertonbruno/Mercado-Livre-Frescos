package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.services.mappers.BatchStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchStockService {
    private final ProductService productService;
    private final BatchStockRepository batchStockRepository;

    private final BatchStockMapper batchStockMapper;

    public BatchStock createFromDTO(BatchStockDTO batchStockDTO) {
        Product product = productService.findById(batchStockDTO.getProductId());

        BatchStock batchStock = batchStockMapper.toBatchStock(batchStockDTO);
        batchStock.setId(null);
        batchStock.setProduct(product);
        return batchStock;
    }

    public BatchStock updateFromDTO(BatchStockDTO batchStockDTO){
        Product product = productService.findById(batchStockDTO.getProductId());
        findById(batchStockDTO.getBatchNumber());

        BatchStock batchStock = batchStockMapper.toBatchStock(batchStockDTO);
        batchStock.setId(batchStockDTO.getBatchNumber());
        batchStock.setProduct(product);
        return batchStock;
    }

    public BatchStock findById(Long batchNumber) {
        return batchStockRepository.findById(batchNumber).orElseThrow(() -> new EntityNotFoundException("Lote com ID " + batchNumber + " não encontrado."));
    }
}
