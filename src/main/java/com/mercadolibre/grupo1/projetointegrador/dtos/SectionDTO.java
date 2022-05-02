package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
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

    public static SectionDTO fromSection(Warehouse fromWarehouse, SectionDTO fromSection){
        return new SectionDTO(
                fromWarehouse.getId(),
                fromSection.getSectionCode()
        );
    }
}
