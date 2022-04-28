package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindProductResponseDTO {
    private SectionDTO section;
    private String productId;
    List<BatchStockResumeDTO> batchStock;

    FindProductResponseDTO(Long productId, Set<BatchStock> batchStocks, Long sectionId, Long warehouseId) {
        batchStock = new ArrayList<>();
        for (BatchStock batch : batchStocks) {
            batchStock.add(BatchStockResumeDTO.fromBatchItem(batch));
        }
        section = new SectionDTO(sectionId, warehouseId);
        this.productId = String.valueOf(productId);
    }
}
