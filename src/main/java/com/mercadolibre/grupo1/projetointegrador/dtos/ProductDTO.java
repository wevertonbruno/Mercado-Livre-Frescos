package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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
    @NotNull(message = "O nome do produto não pode estar vazio")
    private String nome;
    @NotNull(message = "O volume do produto não pode estar vazio")
    private Double volume;
    @NotNull(message = "A preço não pode estar vazio")
    @DecimalMin("0.00")
    private BigDecimal price;
    @NotNull(message = "A categoria não pode estar vazia")
    private ProductCategory category;
}
