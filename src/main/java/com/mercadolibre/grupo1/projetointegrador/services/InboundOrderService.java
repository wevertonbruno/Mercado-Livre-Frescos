package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.OvercapacityException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidOperationException;
import com.mercadolibre.grupo1.projetointegrador.repositories.InboundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servico de cadastro de inbound orders
 * @author Weverton Bruno
 */
@Service
@RequiredArgsConstructor
public class InboundOrderService {

    private final SectionService sectionService;
    private final BatchStockService batchStockService;

    private final InboundOrderRepository inboundOrderRepository;

    /**
     * Valida e salva uma ordem de entrada
     * Todos os lotes do payload sao validados e registrados como um novo lote na sessao
     * @param inboundOrderDTO
     * @author Weverton Bruo
     */
    @Transactional
    public InboundOrderResponseDTO createInboundOrder(InboundOrderDTO inboundOrderDTO) {
        InboundOrder inboundOrder = checkAndCreateInboundOrder(inboundOrderDTO);
        List<BatchStock> batchStocks = createBatchStock(inboundOrderDTO.getBatchStock());

        //verifica se os produtos sáo validos para esta sessao
        checkProductCategory(batchStocks, inboundOrder.getSection().getCategory());

        //Verifica a capacidade da sessao
        checkSectionCapacity(inboundOrder.getSection(), batchStocks);

        InboundOrder createdOrder = inboundOrderRepository.save(inboundOrder);
        batchStocks.forEach(batch -> batch.setInboundOrder(createdOrder));
        createdOrder.getBatchStock().addAll(batchStocks);

        batchStockService.saveAll(batchStocks);

        return InboundOrderResponseDTO.createFromInboundOrder(createdOrder);
    }

    /**
     * Valida e atualiza uma ordem de entrada
     * Uma excecao sera lancada caso a ordem de entrada nao exista
     * Os lotes com Id serao validados e atualizados, os lotes sem id serao validados e criados
     * @param id
     * @param inboundOrderDTO
     * @author Weverton Bruno
     */
    @Transactional
    public InboundOrderResponseDTO updateOrder(Long id, InboundOrderDTO inboundOrderDTO) {
        InboundOrder inboundOrder = findById(id);
        List<BatchStock> batchStocks = updateBatchStock(inboundOrderDTO.getBatchStock(), inboundOrder.getId());

        //verifica se os produtos sáo validos para esta sessao
        checkProductCategory(batchStocks, inboundOrder.getSection().getCategory());

        //Verifica a capacidade da sessao
        checkSectionCapacity(inboundOrder.getSection(), batchStocks);

        batchStocks.forEach(batch -> batch.setInboundOrder(inboundOrder));
        inboundOrder.getBatchStock().clear();
        inboundOrder.getBatchStock().addAll(batchStocks);

        InboundOrder updatedOrder = inboundOrderRepository.save(inboundOrder);

        return InboundOrderResponseDTO.createFromInboundOrder(updatedOrder);
    }

    /**
     * Busca uma ordem de entrada pelo ID
     * @param id
     * @author Weverton Bruno
     */
    public InboundOrder findById(Long id) {
        return inboundOrderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ordem com ID " + id + " não encontrada"));
    }

    /**
     * Valida se a sessao e o armazem da ordem de entrada é valido
     * Caso seja valido um objeto da ordem de entrada é instanciado,
     * setando o section e a data de compra
     * @param inboundOrderDTO
     * @author Weverton Bruno
     */
    private InboundOrder checkAndCreateInboundOrder(InboundOrderDTO inboundOrderDTO) {
        Section section = sectionService.findBySectionDto(inboundOrderDTO.getSection());

        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(section);
        inboundOrder.setOrderDate(inboundOrderDTO.getOrderDate());

        return inboundOrder;
    }

    /**
     * Metodo que gera uma lista de novas instancias de batchstock
     * @param batchStockDTO
     * @author Weverton Bruno
     */
    private List<BatchStock> createBatchStock(List<BatchStockDTO> batchStockDTO){
        return batchStockDTO.stream()
                .map(batchStockItem -> {
                    BatchStock batchItem = batchStockService.createFromDTO(batchStockItem);
                    return batchItem;
                })
                .collect(Collectors.toList());
    }

    /**
     * Metodo que cria uma lista de instancias de batchstock
     * Se o batchstock for novo (sem id) é criado
     * Se o batchstock possuir ID ele é validado para garantir que nao seja de outra ordem de entrada
     * @param batchStockDTO
     * @author Weverton Bruno
     */
    private List<BatchStock> updateBatchStock(List<BatchStockDTO> batchStockDTO, Long orderId){
        //Cria novos lotes
        List<BatchStock> newBatches = createBatchStock(
                batchStockDTO.stream().filter(item -> item.getBatchNumber() == null).collect(Collectors.toList()));

        //Atualiza os lotes ja existentes evitando lotes duplicados
        Set<Long> distinctBatches = new HashSet<>();
        List<BatchStock> existentsBatches = batchStockDTO.stream()
                .filter(item -> item.getBatchNumber() != null)
                .filter(e -> distinctBatches.add(e.getBatchNumber()))
                .map(batchStockItem -> {
                    BatchStock batchItem = batchStockService.updateFromDTO(batchStockItem);

                    //Verifica se o inboundOrder do batch é o mesmo da request
                    if(!batchItem.getInboundOrder().getId().equals(orderId)){
                        throw new InvalidOperationException("O lote de ID " + batchItem.getId() + " não pertence a esta Ordem de Entrada!");
                    }
                    return batchItem;
                })
                .collect(Collectors.toList());

        //junta todos os lotes
        existentsBatches.addAll(newBatches);

        return existentsBatches;
    }

    /**
     * Verifica se todos os lotes pertencem a categoria selecionada
     * @param batchStocks
     * @param category
     * @Author Weverton Bruno
     */
    private void checkProductCategory(List<BatchStock> batchStocks, ProductCategory category){
        batchStocks.forEach(batch -> {
            if(!batch.getProduct().getCategory().equals(category))
                throw new InvalidCategoryException(String.format("Só é permitido produtos da categoria %s nesta sessão.", category));
        });
    }

    /**
     * Verifica a capacidade da sessao com base nos lotes
     * caso os lotes já estejam cadastrados, o excedente é contado para gerar o volume
     * @param section
     * @param batchStocks
     * @author Weverton Bruno
     */
    private void checkSectionCapacity(Section section, List<BatchStock> batchStocks){
        //Busca o estoque atual da sessao e calcula o volume preenchido
        Set<BatchStock> currentStock = batchStockService.findBatchStockBySectionId(section.getId());
        Double filledVolume = currentStock.stream().reduce(0.0, (total, batchItem) -> total + batchItem.getVolume(), Double::sum);

        //Verifica o estoque atual e junta com o novo estoque
        List<BatchStock> targetStock = new ArrayList<>();
        List<Long> batches = batchStocks.stream().filter(item -> item.getId() != null).mapToLong(item -> item.getId()).boxed().collect(Collectors.toList());

        // Cria o estoque alvo para calcular o novo volume
        targetStock.addAll(batchStocks);
        targetStock.addAll(currentStock.stream().filter(item -> !batches.contains(item.getId())).collect(Collectors.toList()));

        //Calcula o novo volume
        Double targetVolume = targetStock.stream().reduce(0.0, (total, batchItem) -> total + batchItem.getVolume(), Double::sum);

        //Verifica a capacidade
        if(section.getCapacity() < targetVolume){
            throw new OvercapacityException(String.format("Capacidade da sessao excedida! Capacidade total da sessao: %.2fm3, Capacidade ocupada: %.2fm3, Após a operacao: %.2fm3.", section.getCapacity(), filledVolume, targetVolume));
        }
    }

}
