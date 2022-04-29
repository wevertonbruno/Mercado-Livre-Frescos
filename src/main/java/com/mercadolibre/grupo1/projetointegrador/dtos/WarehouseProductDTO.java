package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nayara Coca
 * dto para retorno de produtos no warehouse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductDTO {
    private Long warehouseCode;
    private Long totalQuantity;

}
