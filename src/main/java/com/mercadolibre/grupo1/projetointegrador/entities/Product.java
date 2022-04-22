package com.mercadolibre.grupo1.projetointegrador.entities;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Nayara Coca
 * Criação da entidade products
 * Gerando getters e setters
 */

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String nome;
    private Double volume;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
}
