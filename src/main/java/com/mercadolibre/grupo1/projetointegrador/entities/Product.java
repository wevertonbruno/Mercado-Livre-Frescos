package com.mercadolibre.grupo1.projetointegrador.entities;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.*;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

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
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double volume;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

}
