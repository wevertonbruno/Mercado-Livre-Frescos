package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;

import java.util.List;
import java.util.stream.Collectors;

/*
@author Gabriel Essenio
Construindo service de para tratar as requisitos da busca de produtos
 */

@Service
@RequiredArgsConstructor
public class ProductService {

    // Faz injeção de dependecia do repositorio de produtos
    private final ProductRepository productRepository;

    //Metodo que chama Repositorio de Produto e retorna todos produtos cadastrados
    public List<ProductDTO> listAllProducts(){
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(),product.getNome(),product.getVolume(),product.getPrice(),product.getCategory()))
                .collect(Collectors.toList());
    }

    //Metodo que chama Repositorio de Produto e retorna produtos de acordo com a categoria passada pelo parametro)
    public List<ProductDTO> listProductByCategory(ProductCategory productCategory) throws Exception {
        List<Product> productsByCategory = productRepository.findAllByCategory(productCategory);
        if (productsByCategory.isEmpty()){
            throw new Exception("Categoria não encontrada");
        }
        return productsByCategory.stream()
                .map(product -> new ProductDTO(product.getId(),product.getNome(),product.getVolume(),product.getPrice(),product.getCategory()))
                .collect(Collectors.toList());
    }


    /**
     * @author Weverton Bruno
     * @param productId
     */
    public Product findById(Long productId){
        return productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Produto com ID " + productId + " não encontrado"));
    }
}
