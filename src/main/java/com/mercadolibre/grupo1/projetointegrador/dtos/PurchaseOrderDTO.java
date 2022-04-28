package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ederson Rodrigues Araujo
 * purchase order dto de acordo com o payload
 */

@Data
public class PurchaseOrderDTO {

    @Valid
    private PurchaseOrder purchaseOrder;

    @Data
    @Builder
    public static class ProductItemDTO {
        @NotNull(message = "Não é permitido valor nulo")
        private Long productId;
        @NotNull(message = "Não é permitido valor nulo")
        private Integer quantity;
    }

    @Data
    public static class PurchaseOrder {
        private LocalDate date;
        @NotNull(message = "Não é permitido valor nulo")
        private Long buyerId;
        private PurchaseOrderStatusDTO orderStatus;
        @Valid
        private List<ProductItemDTO> products;

    }

    // para enviar a resposta de com o total dos produtos
    @Data
    @Builder
    public static class Response {
        private BigDecimal totalPrice;
    }
}
