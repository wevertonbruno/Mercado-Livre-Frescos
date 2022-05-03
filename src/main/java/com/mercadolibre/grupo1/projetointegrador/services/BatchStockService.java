package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ForbiddenException;
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
    private final AuthService authService;

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
        Agent agent = authService.getPrincipalAs(Agent.class);
        checkAgentWarehouse(agent, sectionId);

        return batchStockRepository.findStockBySectionIdAndDueDateBetween(sectionId, LocalDate.now(), LocalDate.now().plusDays(expiresIn))
                .stream().map(BatchStockDTO::toSimpleBatchDTO)
                .collect(Collectors.toList());
    }

    private void checkAgentWarehouse(Agent agent, Long sectionId) {
        if(!agent.getWarehouse().getSections().stream().anyMatch(section -> section.getId().equals(sectionId))){
            throw new ForbiddenException("O representante logado não pertence a esse armazém!");
        }
    }

    /**
     * Busca todos os lotes de um armazem pela categoria e pelos dias de vencimento
     * @param productCategory
     * @param expiresIn
     * @param direction
     * @author Weverton Bruno
     */
    public List<BatchStockDTO.SimpleBatchStockDTO> findBatchStockByCategoryAndExpiresIn(ProductCategory productCategory, Long expiresIn, Sort.Direction direction) {
        Agent agent = authService.getPrincipalAs(Agent.class);
        return batchStockRepository.findWarehouseStockByCategoryAndDueDateBetween(agent.getWarehouse().getId(), productCategory, LocalDate.now(), LocalDate.now().plusDays(expiresIn), Sort.by(direction, "dueDate"))
                .stream().map(BatchStockDTO::toSimpleBatchDTO)
                .collect(Collectors.toList());
    }
}
