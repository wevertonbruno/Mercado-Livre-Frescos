package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductItemDTO {
        @NotNull(message = "ProductId não é permitido valor nulo!")
        private Long productId;

        @NotNull(message = "Quantidade não é permitido valor nulo!")
        @Min(value = 1, message = "Quantidade mínima permitida: 1")
        private Integer quantity;
    }

    @Data
    public static class PurchaseOrder {
        private LocalDate date;
        @NotNull(message = "BuyerId não é permitido valor nulo!")
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
