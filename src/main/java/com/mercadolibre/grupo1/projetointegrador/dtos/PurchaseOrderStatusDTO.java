package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderStatusDTO {
    
    private OrderStatus status;

}
