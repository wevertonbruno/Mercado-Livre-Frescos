package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Nayara Coca
 * Classe responsável por filtra dados do estoque no armazém
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStock {
    private Long id;
    @NotNull
    private Integer batchNumber;
    @NotNull
    private Float currentTemperature;
    @NotNull
    private Float minimumTemperature;
    @NotNull
    private Integer initialQuantity;
    @NotNull
    private Integer currentQuantity;
    @NotNull
    private LocalDateTime manufacturingDateTime;
    @NotNull
    private LocalDate dueDate;
}
