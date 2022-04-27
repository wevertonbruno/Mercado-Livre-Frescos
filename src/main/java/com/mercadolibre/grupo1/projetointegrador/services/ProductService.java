package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/*
@author Gabriel Essenio
Construindo service de para tratar as requisitos da busca de produtos
 */
@Service
public class ProductService {
    /*
    @author Gabriel Essenio
    Faz injeção de dependecia do repositorio de produtos
     */
    @Autowired
    private ProductRepository productRepository;
    /*
    @author Gabriel Essenio
    Metodo que chama Repositorio de Produto e retorna todos produtos cadastrados
     */
    public List<ProductDTO> listAllProducts(){
        List<ProductDTO> allProducts =  productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(),product.getNome(),product.getVolume(),product.getPrice(),product.getCategory()))
                .collect(Collectors.toList());
        if(allProducts.isEmpty()){
            throw new ListIsEmptyException("Nenhum produto cadastrado");
        }
        return allProducts;
    }
    /*
    @author Gabriel Essenio
    Metodo que chama Repositorio de Produto e retorna produtos de acordo com a categoria passada pelo parametro)
    Verifica se a lista esta vazia e se a Categoria esta listada no Enum
     */
    public List<ProductDTO> listProductByCategory(ProductCategory productCategory){
        List<Product> productsByCategory = productRepository.findAllByCategory(productCategory);
        if (productsByCategory.isEmpty()){
            throw new ListIsEmptyException("Categoria não encontrada");
        }
        return productsByCategory.stream()
                .map(product -> new ProductDTO(product.getId(),product.getNome(),product.getVolume(),product.getPrice(),product.getCategory()))
                .collect(Collectors.toList());
    }
}