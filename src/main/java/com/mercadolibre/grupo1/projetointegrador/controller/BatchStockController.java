package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
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

    /**
     * Controlador para busca todos os lotes filtrados por sessao e periodo de validade
     * @param expiresIn dias para expirar o lote
     * @param sectionId identificador da sessao
     * @author Weverton Bruno
     */
    @GetMapping("/due-date")
    public ResponseEntity<List<BatchStockDTO.SimpleBatchStockDTO>> findByDueDateAndSectionId(
            @RequestParam(name = "expires_in") Long expiresIn,
            @RequestParam(name = "section_code") Long sectionId){
        List<BatchStockDTO.SimpleBatchStockDTO> batchStocks = batchStockService.findBatchStockBySectionIdAndExpiresIn(sectionId, expiresIn);
        return ResponseEntity.ok(batchStocks);
    }

    /**
     * Controlador para busca todos os lotes do armazem filtrados por categoria e periodo de validade
     * @param expiresIn dias para vencer o lote
     * @param productCategory categoria do lote que pode ser FRESCO, CONGELADO, REFRIGERADO
     * @param direction direcao da ordenacao por data de validade ASC = ascendente, DESC = descendente
     * @author Weverton Bruno
     */
    @GetMapping("/due-date/list")
    public ResponseEntity<List<BatchStockDTO.SimpleBatchStockDTO>> findByDueDateAndSectionId(
            @RequestParam(name = "expires_in") Long expiresIn,
            @RequestParam(name = "category") ProductCategory productCategory,
            @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction){
        List<BatchStockDTO.SimpleBatchStockDTO> batchStocks = batchStockService.findBatchStockByCategoryAndExpiresIn(productCategory, expiresIn, direction);
        return ResponseEntity.ok(batchStocks);
    }
}
