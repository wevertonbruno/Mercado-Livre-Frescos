package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Nayara Coca
 * Classe responsável por filtrar dados dos produtos enviados para o cliente
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotNull
    private String nome;
    @NotNull
    private Double volume;
    @NotNull
    private BigDecimal price;
    @NotNull
    private ProductCategory category;
}
