package com.mercadolibre.grupo1.projetointegrador.dtos;

// DTO pegando o id da Section e o id da Warehouse
// @author Ederson Rodrigues Araujo

import lombok.Data;

@Data
public class SectionDTO {

    private Long sectionCode;
    private Long warehouseCode;
}
