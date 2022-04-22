package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
/**
 * @author Nayara Coca
 * Classe responsável por filtrar dados dos pedido
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderDTO {
    //verificar se será gerado automaticamente
    private Integer orderNumber;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private String section;
}
