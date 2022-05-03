package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nayara Coca
 * Criação repositório de product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    /**
     *@author Gabriel Essenio
     * Metodos que fazem query de acordo com o nome do metodo
     */
    List<Product> findAll();

    List<Product> findAllByCategory(ProductCategory productCategory);

}
