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
public class BatchStockDTO {
    private Long id;
    @NotNull (message = "O número do lote não pode estar vazio")
    private Integer batchNumber;
    @NotNull (message = "A temperatura atual não pode estar vazia")
    private Float currentTemperature;
    @NotNull(message = "A temperatura mínima não pode estar vazia")
    private Float minimumTemperature;
    @NotNull(message = "A quantidade inicial não pode estar vazia")
    private Integer initialQuantity;
    @NotNull(message = "A quantidade atual não pode estar vazia")
    private Integer currentQuantity;
    @NotNull(message = "A data de fabricação não pode estar vazia")
    private LocalDateTime manufacturingDateTime;
    @NotNull(message = "A data de validade não pode estar vazia")
    private LocalDate dueDate;
}
