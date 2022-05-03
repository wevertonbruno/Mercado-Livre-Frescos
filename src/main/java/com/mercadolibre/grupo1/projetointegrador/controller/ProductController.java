package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Gabriel Essenio
 * Controller de Product ,cria os endpoints e trata o retorno de acordo com cada tipo de endpoint
 */
@RestController
@RequestMapping("/api/v1/fresh-products/")
public class ProductController {
    // Faz injecao de dependencia da camada service
    @Autowired
    private ProductService productService;


    // Endpoint do tipo Get , que faz requisicao de todos os produtos cadastrados
    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProduct(){
        List<ProductDTO> allProducts = productService.listAllProducts();
        return ResponseEntity.ok().body(allProducts);
    }

    // Endpoint do tipo Get, que faz requisicao de produtos de acordo com os status passado no parametro
    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listProductForCategory( @RequestParam(required = false, name = "status") ProductCategory productCategory) {
        List<ProductDTO> productByCategory = productService.listProductByCategory(productCategory);
        return ResponseEntity.ok().body(productByCategory);
    }
}
