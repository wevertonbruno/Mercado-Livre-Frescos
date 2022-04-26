package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExcededCapacityException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import com.mercadolibre.grupo1.projetointegrador.repositories.InboundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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
        inboundOrder = inboundOrderRepository.save(inboundOrder);

        return InboundOrderResponseDTO.createFromInboundOrder(inboundOrder);
    }

    @Transactional
    public InboundOrderResponseDTO updateOrder(Long id, InboundOrderDTO inboundOrder) {
        return null;
    }

    private InboundOrder checkAndCreateInboundOrder(InboundOrderDTO inboundOrderDTO) {
        Section section = sectionService.findBySectionDto(inboundOrderDTO.getSection());
        List<BatchStock> batchStocks = createBatchStock(inboundOrderDTO.getBatchStock(), section);

        //Verifica se a sessao tem capacidade para receber o estoque
        checkSectionCapacity(section, batchStocks);

        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(section);
        inboundOrder.setOrderDate(inboundOrderDTO.getOrderDate());
        inboundOrder.setBatchStock(batchStocks);

        return inboundOrder;
    }

    private List<BatchStock> createBatchStock(List<BatchStockDTO> batchStockDTO, Section section){
        return batchStockDTO.stream().map( batchStockItem -> {
            BatchStock batchItem = batchStockService.createFromDTO(batchStockItem);
            checkProductAndSectionCategory(batchItem.getProduct(), section);

            return batchItem;
        }).collect(Collectors.toList());
    }

    private void checkProductAndSectionCategory(Product product, Section section){
        if(!product.getCategory().equals(section.getCategory()))
            throw new InvalidCategoryException(String.format("Só é permitido produtos da categoria %s nesta sessão.", section.getCategory()));
    }

    private void checkSectionCapacity(Section section, List<BatchStock> batchStocks){
        Double inboundVolume = batchStocks.stream().reduce(0.0, (total, batchItem) -> batchItem.getVolume() + total, Double::sum);
        Double remainingVolume = section.getRemainingCapacity();
        if(inboundVolume > remainingVolume){
            throw new ExcededCapacityException("A ordem de entrada possui um volume de " + inboundVolume + "m3. A capacidade restante da sessão é de " + remainingVolume + "m3!");
        }
    }

}
