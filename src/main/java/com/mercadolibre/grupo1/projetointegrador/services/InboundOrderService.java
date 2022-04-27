package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExcededCapacityException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import com.mercadolibre.grupo1.projetointegrador.repositories.InboundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servico de cadastro de inbound orders
 * @author Weverton Bruno
 *
 */
@Service
@RequiredArgsConstructor
public class InboundOrderService {

    private final SectionService sectionService;
    private final BatchStockService batchStockService;

    private final InboundOrderRepository inboundOrderRepository;

    @Transactional
    public InboundOrderResponseDTO createInboundOrder(InboundOrderDTO inboundOrderDTO) {
        InboundOrder inboundOrder = checkAndCreateInboundOrder(inboundOrderDTO);
        List<BatchStock> batchStocks = createBatchStock(inboundOrderDTO.getBatchStock(), inboundOrder.getSection());

        //Verifica a capacidade da sessao
        checkSectionCapacity(inboundOrder.getSection(), batchStocks);

        InboundOrder createdOrder = inboundOrderRepository.save(inboundOrder);
        batchStocks.forEach(batch -> batch.setInboundOrder(createdOrder));
        createdOrder.getBatchStock().addAll(batchStocks);

        batchStockService.saveAll(batchStocks);

        return InboundOrderResponseDTO.createFromInboundOrder(createdOrder);
    }

    @Transactional
    public InboundOrderResponseDTO updateOrder(Long id, InboundOrderDTO inboundOrderDTO) {
        InboundOrder inboundOrder = findById(id);
        List<BatchStock> batchStocks = updateBatchStock(inboundOrderDTO.getBatchStock(), inboundOrder.getSection());

        //Verifica a capacidade da sessao
        checkSectionCapacity(inboundOrder.getSection(), batchStocks);

        batchStocks.forEach(batch -> batch.setInboundOrder(inboundOrder));
        inboundOrder.getBatchStock().clear();
        inboundOrder.getBatchStock().addAll(batchStocks);

        InboundOrder updatedOrder = inboundOrderRepository.save(inboundOrder);

        return InboundOrderResponseDTO.createFromInboundOrder(updatedOrder);
    }

    public InboundOrder findById(Long id) {
        return inboundOrderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ordem com ID " + id + " não encontrada"));
    }

    private InboundOrder checkAndCreateInboundOrder(InboundOrderDTO inboundOrderDTO) {
        Section section = sectionService.findBySectionDto(inboundOrderDTO.getSection());

        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(section);
        inboundOrder.setOrderDate(inboundOrderDTO.getOrderDate());

        return inboundOrder;
    }

    private List<BatchStock> createBatchStock(List<BatchStockDTO> batchStockDTO, Section section){
        return batchStockDTO.stream()
                .map(batchStockItem -> {
                    BatchStock batchItem = batchStockService.createFromDTO(batchStockItem);
                    checkProductAndSectionCategory(batchItem.getProduct(), section);
                    return batchItem;
                })
                .collect(Collectors.toList());
    }

    private List<BatchStock> updateBatchStock(List<BatchStockDTO> batchStockDTO, Section section){
        //Cria novos lotes
        List<BatchStock> newBatches = createBatchStock(
                batchStockDTO.stream().filter(item -> item.getBatchNumber() == null).collect(Collectors.toList()),
                section);

        //Atualiza os lotes ja existentes evitando lotes duplicados
        Set<Long> distinctBatches = new HashSet<>();
        List<BatchStock> existentsBatches = batchStockDTO.stream()
                .filter(item -> item.getBatchNumber() != null)
                .filter(e -> distinctBatches.add(e.getBatchNumber()))
                .map(batchStockItem -> {
                    BatchStock batchItem = batchStockService.updateFromDTO(batchStockItem);
                    checkProductAndSectionCategory(batchItem.getProduct(), section);
                    return batchItem;
                })
                .collect(Collectors.toList());

        //junta todos os lotes
        existentsBatches.addAll(newBatches);

        return existentsBatches;
    }

    private void checkProductAndSectionCategory(Product product, Section section){
        if(!product.getCategory().equals(section.getCategory()))
            throw new InvalidCategoryException(String.format("Só é permitido produtos da categoria %s nesta sessão.", section.getCategory()));
    }

    private void checkSectionCapacity(Section section, List<BatchStock> batchStocks){
        Set<BatchStock> currentStock = batchStockService.findBatchStockBySectionId(section.getId());
        Double filledVolume = currentStock.stream().reduce(0.0, (total, batchItem) -> total + batchItem.getVolume(), Double::sum);

        //Verifica o estoque atual e junta com o novo estoque
        List<BatchStock> targetStock = new ArrayList<>();
        List<Long> batches = batchStocks.stream().filter(item -> item.getId() != null).mapToLong(item -> item.getId()).boxed().collect(Collectors.toList());

        targetStock.addAll(batchStocks);
        targetStock.addAll(currentStock.stream().filter(item -> !batches.contains(item.getId())).collect(Collectors.toList()));

        Double targetVolume = targetStock.stream().reduce(0.0, (total, batchItem) -> total + batchItem.getVolume(), Double::sum);

        if(section.getCapacity() < targetVolume){
            throw new ExcededCapacityException(String.format("Capacidade da sessao excedida! Capacidade total da sessao: %.2fm3, Capacidade ocupada: %.2fm3, Após a operacao: %.2fm3.", section.getCapacity(), filledVolume, targetVolume));
        }
    }

}
