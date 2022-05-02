package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import lombok.*;

/**
 * @author Nayara Coca
 * dto para retorno de quantidade de produtos no warehouse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductDTO {
   private Long warehouseCode;
    private Long totalQuantity;

}
