package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.services.mappers.BatchStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe de servico dos lotes
 * @author Weverton Bruno
 */
@Service
@RequiredArgsConstructor
public class BatchStockService {
    private final ProductService productService;
    private final BatchStockRepository batchStockRepository;

    private final BatchStockMapper batchStockMapper;

    /**
     * Inicializacao de uma entidade de lote a partir do DTO
     * @param batchStockDTO
     * @return
     */
    public BatchStock createFromDTO(BatchStockDTO batchStockDTO) {
        Product product = productService.findById(batchStockDTO.getProductId());

        BatchStock batchStock = batchStockMapper.toBatchStock(batchStockDTO);
        batchStock.setId(null);
        batchStock.setProduct(product);
        return batchStock;
    }

    /**
     * Atualizacao de uma entidade de lote ja existente pelo DTO
     * @param batchStockDTO
     * @return
     */
    public BatchStock updateFromDTO(BatchStockDTO batchStockDTO){
        Product product = productService.findById(batchStockDTO.getProductId());
        BatchStock batchStock = findById(batchStockDTO.getBatchNumber());

        BatchStock updatedBatch = batchStockMapper.toBatchStock(batchStockDTO);
        updatedBatch.setId(batchStockDTO.getBatchNumber());
        updatedBatch.setProduct(product);
        updatedBatch.setInboundOrder(batchStock.getInboundOrder());
        return updatedBatch;
    }

    /**
     * Salva um estoque com varios lotes de produtos
     * @param batchStocks
     * @author Weverton BRuno
     */
    public void saveAll(List<BatchStock> batchStocks){
        batchStockRepository.saveAll(batchStocks);
    }

    /**
     * Faz a busca pelo Id de um BatchStock
     * @param batchNumber
     * @author Weverton Bruno
     */
    public BatchStock findById(Long batchNumber) {
        return batchStockRepository.findById(batchNumber).orElseThrow(() -> new EntityNotFoundException("Lote com ID " + batchNumber + " não encontrado."));
    }

    /**
     * Busca todos os lotes de uma sessao
     * @param sectionId
     * @author Weverton Bruno
     */
    public Set<BatchStock> findBatchStockBySectionId(Long sectionId){
        return batchStockRepository.findStockBySectionId(sectionId);
    }

    /**
     * Busca todos os lotes de uma sessao pelos dias de vencimento
     * @param sectionId
     * @param expiresIn
     * @author Weverton Bruno
     */
    public List<BatchStockDTO.SimpleBatchStockDTO> findBatchStockBySectionIdAndExpiresIn(Long sectionId, Long expiresIn) {
        return batchStockRepository.findStockBySectionIdAndDueDateBetween(sectionId, LocalDate.now(), LocalDate.now().plusDays(expiresIn))
                .stream().map(BatchStockDTO::toSimpleBatchDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca todos os lotes de um armazem pela categoria e pelos dias de vencimento
     * @param productCategory
     * @param expiresIn
     * @param direction
     * @author Weverton Bruno
     */
    public List<BatchStockDTO.SimpleBatchStockDTO> findBatchStockByCategoryAndExpiresIn(ProductCategory productCategory, Long expiresIn, Sort.Direction direction) {
        return batchStockRepository.findWarehouseStockByCategoryAndDueDateBetween(1L, productCategory, LocalDate.now(), LocalDate.now().plusDays(expiresIn), Sort.by(direction, "dueDate"))
                .stream().map(BatchStockDTO::toSimpleBatchDTO)
                .collect(Collectors.toList());
    }
}
