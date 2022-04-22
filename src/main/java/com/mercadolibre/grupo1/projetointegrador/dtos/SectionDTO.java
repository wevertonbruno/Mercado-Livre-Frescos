package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Ederson Rodrigues Araujo
 * DTO pegando o id da Section e o id da Warehouse
 */

@Data
public class SectionDTO {

    @NotNull
    private Long sectionCode;
    @NotNull
    private Long warehouseCode;
}
