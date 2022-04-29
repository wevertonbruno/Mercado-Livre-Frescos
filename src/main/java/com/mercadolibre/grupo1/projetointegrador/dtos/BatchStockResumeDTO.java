package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class BatchStockResumeDTO {
    private Long batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;

    public static BatchStockResumeDTO fromBatchItem(BatchStock batchItem) {
        return new BatchStockResumeDTO(
                batchItem.getId(),
                batchItem.getCurrentQuantity(),
                batchItem.getDueDate()
        );
    }

}
