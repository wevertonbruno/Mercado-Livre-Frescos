package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nayara Coca
 * Classe responsável por filtrar dados dos pedido
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDTO {
    private Long orderNumber;
    @NotNull(message = "A data do pedido não pode estar vazia")
    private LocalDate orderDate;
    @NotNull(message = "A sessão não pode estar vazia")
    private SectionDTO section;
    @Valid
    @NotEmpty(message = "A ordem de entrada precisa ter pelo menos um lote de produto")
    private List<BatchStockDTO> batchStock;

    public static InboundOrderDTO fromInboundOrder(InboundOrder inboundOrder){
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectionCode(inboundOrder.getSection().getId());
        sectionDTO.setWarehouseCode(inboundOrder.getSection().getWarehouse().getId());

        return new InboundOrderDTO(
                inboundOrder.getId(),
                inboundOrder.getOrderDate(),
                sectionDTO,
                inboundOrder.getBatchStock().stream()
                        .map(BatchStockDTO::fromBatchItem)
                        .collect(Collectors.toList()));
    }
}
