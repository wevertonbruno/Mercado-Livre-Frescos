package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderStatusDTO {
    
    private OrderStatus status;

}
