package com.mercadolibre.grupo1.projetointegrador.unitario;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
@author Gabriel Essenio
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private List<ProductDTO> gerarProductDTO(){
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setProductId(1L);
        productDTO1.setNome("Product1");
        productDTO1.setVolume(20.0);
        productDTO1.setPrice(BigDecimal.valueOf(350));
        productDTO1.setCategory(ProductCategory.FRESCO);

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setProductId(2L);
        productDTO2.setNome("Product2");
        productDTO2.setVolume(30.0);
        productDTO2.setPrice(BigDecimal.valueOf(450));
        productDTO2.setCategory(ProductCategory.REFRIGERADO);

        ProductDTO productDTO3 = new ProductDTO();
        productDTO3.setProductId(3L);
        productDTO3.setNome("Product3");
        productDTO3.setVolume(50.0);
        productDTO3.setPrice(BigDecimal.valueOf(550));
        productDTO3.setCategory(ProductCategory.FRESCO);

        return Arrays.asList(productDTO1,productDTO2,productDTO3);
    }

    private List<Product> gerarProduct(){
        Product product1 = new Product();
        product1.setId(1L);
        product1.setNome("Product1");
        product1.setVolume(20.0);
        product1.setPrice(BigDecimal.valueOf(350));
        product1.setCategory(ProductCategory.FRESCO);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setNome("Product2");
        product2.setVolume(30.0);
        product2.setPrice(BigDecimal.valueOf(450));
        product2.setCategory(ProductCategory.REFRIGERADO);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setNome("Product3");
        product3.setVolume(50.0);
        product3.setPrice(BigDecimal.valueOf(550));
        product3.setCategory(ProductCategory.FRESCO);

        return Arrays.asList(product1,product2, product3);
    }

    @Test
    @DisplayName("Teste se a lista retorna todos os produtos cadastrados corretamente")
    public void testAllListProducts(){
        List<Product> allProducts = gerarProduct();
        Mockito.when(productRepository.findAll()).thenReturn(allProducts);
        List<ProductDTO> serviceProductDTO = productService.listAllProducts();
        Assertions.assertEquals(allProducts.get(0).getNome(), serviceProductDTO.get(0).getNome());
        Assertions.assertEquals(allProducts.get(1).getNome(), serviceProductDTO.get(1).getNome());
    }

    @Test
    @DisplayName("Teste se a lista retorna vazia quando nenhum produto tiver cadastrado")
    public void testListIsEmpty(){
        Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());
        Throwable listIsEmptyException = Assertions.assertThrows(ListIsEmptyException.class, () -> productService.listAllProducts());
        Assertions.assertEquals(listIsEmptyException.getMessage(), "Nenhum produto cadastrado");
    }

    @Test
    @DisplayName("Teste se retorna lista de produtos quando é passado um status de category")
    public void testListProductByCategory(){
        List<Product> allProducts = gerarProduct();
        List<Product> listByCategory = allProducts.stream().filter(prod -> prod.getCategory().equals(ProductCategory.FRESCO)).collect(Collectors.toList());
        Mockito.when(productRepository.findAllByCategory(ProductCategory.FRESCO)).thenReturn(listByCategory);
        List<ProductDTO> serviceProductByCategory = productService.listProductByCategory(ProductCategory.FRESCO);
        Assertions.assertNotNull(serviceProductByCategory);
    }

    @Test
    @DisplayName("Teste e retorna mensagem correta quando passado uma lista vazia")
    public void testMessageReturnEmptyListProductByCategory(){
        Mockito.when(productRepository.findAllByCategory(ProductCategory.CONGELADO)).thenReturn(new ArrayList<>());
        Throwable listIsEmptyException = Assertions.assertThrows(ListIsEmptyException.class, () -> productService.listProductByCategory(ProductCategory.CONGELADO));
        Assertions.assertEquals(listIsEmptyException.getMessage(), "Categoria não encontrada");
    }
}
