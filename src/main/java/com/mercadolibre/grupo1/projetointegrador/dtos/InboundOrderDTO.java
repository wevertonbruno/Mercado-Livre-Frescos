package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Nayara Coca
 * Classe responsável por filtrar dados dos pedido
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderDTO {
    private Long orderNumber;
    @NotNull(message = "A data do pedido não pode estar vazia")
    private LocalDate orderDate;
    @NotNull(message = "A sessão não pode estar vazia")
    private String section;
    @Valid
    @NotEmpty(message = "A ordem de entrada precisa de um lote")
    private Set<BatchStockDTO> batchStock;

}
