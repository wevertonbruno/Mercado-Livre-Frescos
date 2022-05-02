package com.mercadolibre.grupo1.projetointegrador.entities;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Nayara Coca
 * Criação da entidade products
 * Gerando getters e setters
 */

@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double volume;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

}
