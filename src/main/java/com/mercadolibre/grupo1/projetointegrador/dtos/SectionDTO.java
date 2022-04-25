package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Ederson Rodrigues Araujo
 * DTO pegando o id da Section e o id da Warehouse
 */

@Data
public class SectionDTO {

    @NotNull(message = "Código da sessão não pode ser nulo")
    private Long sectionCode;
    @NotNull(message = "Código da sessão não pode ser nulo")
    private Long warehouseCode;
}
