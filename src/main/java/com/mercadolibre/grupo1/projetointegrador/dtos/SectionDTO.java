package com.mercadolibre.grupo1.projetointegrador.dtos;

// DTO pegando o id da Section e o id da Warehouse
// @author Ederson Rodrigues Araujo

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SectionDTO {

    @NotNull
    private Long sectionCode;
    @NotNull
    private Long warehouseCode;
}
