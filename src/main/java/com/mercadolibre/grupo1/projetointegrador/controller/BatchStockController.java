package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.services.BatchStockService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador do batch stock
 * @author Weverton Bruno
 */
@RestController
@RequestMapping("api/v1/fresh-products")
public class BatchStockController {

    private final BatchStockService batchStockService;

    public BatchStockController(BatchStockService batchStockService) {
        this.batchStockService = batchStockService;
    }

    @GetMapping("/due-date")
    public ResponseEntity<List<BatchStockDTO.SimpleBatchStockDTO>> findByDueDateAndSectionId(
            @RequestParam(name = "expires_in") Long expiresIn,
            @RequestParam(name = "section_code") Long sectionId){
        List<BatchStockDTO.SimpleBatchStockDTO> batchStocks = batchStockService.findBatchStockBySectionIdAndExpiresIn(sectionId, expiresIn);
        return ResponseEntity.ok(batchStocks);
    }

    @GetMapping("/due-date/list")
    public ResponseEntity<List<BatchStockDTO.SimpleBatchStockDTO>> findByDueDateAndSectionId(
            @RequestParam(name = "expires_in") Long expiresIn,
            @RequestParam(name = "category") ProductCategory productCategory,
            @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction){
        List<BatchStockDTO.SimpleBatchStockDTO> batchStocks = batchStockService.findBatchStockByCategoryAndExpiresIn(productCategory, expiresIn, direction);
        return ResponseEntity.ok(batchStocks);
    }
}
