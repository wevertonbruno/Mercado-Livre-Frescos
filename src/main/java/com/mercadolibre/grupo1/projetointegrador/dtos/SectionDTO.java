package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Ederson Rodrigues Araujo
 * DTO pegando o id da Section e o id da Warehouse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {
    @NotNull(message = "Código da sessão não pode ser nulo")
    private Long sectionCode;
    @NotNull(message = "Código do armazém não pode ser nulo")
    private Long warehouseCode;
}
