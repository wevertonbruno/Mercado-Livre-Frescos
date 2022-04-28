package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class InboundOrderResponseDTO {
    private Long orderNumber;
    private List<BatchStockDTO> batchStocks;

    public static InboundOrderResponseDTO createFromInboundOrder(InboundOrder inboundOrder){
        List<BatchStockDTO> batchDto = inboundOrder.getBatchStock().stream().map(BatchStockDTO::fromBatchItem).collect(Collectors.toList());

        return new InboundOrderResponseDTO(inboundOrder.getId(), batchDto);
    }
}
