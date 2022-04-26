package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseOrderDTO {

    @NotNull(message = "Não é permitido valor nulo")
    private LocalDate date;
    @NotNull(message = "Não é permitido valor nulo")
    private Long buyerId;
    private PurchaseOrderStatusDTO orderStatus;
    private List<ProductItemDTO> products;

    @Data
    public static class ProductItemDTO {
        private Long productId;
        private int quantity;
    }
}
