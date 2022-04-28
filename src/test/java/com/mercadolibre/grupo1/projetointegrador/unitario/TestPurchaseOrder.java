package com.mercadolibre.grupo1.projetointegrador.unitario;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Customer;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredProducts;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.CustomerRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestPurchaseOrder {

    @Mock
    BatchStockRepository batchStockRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    PurchaseOrderService purchaseOrderService;

    private PurchaseOrderDTO createPurchaseOrderDTO() {
        PurchaseOrderDTO.PurchaseOrder purchaseOrder = new PurchaseOrderDTO.PurchaseOrder();
        List<PurchaseOrderDTO.ProductItemDTO> products = new ArrayList<>(Arrays.asList(
                PurchaseOrderDTO.ProductItemDTO.builder().productId(1L).quantity(20).build()
        ));
        purchaseOrder.setBuyerId(1L);
        purchaseOrder.setProducts(products);

        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setPurchaseOrder(purchaseOrder);
        return purchaseOrderDTO;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setPassword("123456");
        customer.setCpf("111.111.111-01");
        customer.setEmail("ederson@mercadolivre.com");

        return customer;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setNome("Product");
        product.setPrice(BigDecimal.valueOf(1.));
        product.setVolume(10.);

        return product;
    }
    @Test
    @DisplayName("Testa se retorna a mensagem de error: Usuário não cadastrado")
    public void testandoExcepitionUregistered() {

        // Configuração
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Action
        Throwable messageUnregisteredUser = Assertions.assertThrows(UnregisteredUser.class, () ->
                purchaseOrderService.createPurchaseOrder(purchaseOrderDTO));

        // Verificação
        Assertions.assertEquals(
                messageUnregisteredUser.getMessage(), "Usuário não cadastrado"
        );
    }

    @Test
    @DisplayName("Testa se retorna exception de produto nao cadastrado")
    public void testExceptionUregisteredProduct () {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        Customer customer = createCustomer();

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Throwable messageUnregisteredProduct = Assertions.assertThrows(UnregisteredProducts.class, () ->
                purchaseOrderService.createPurchaseOrder(purchaseOrderDTO));

        Assertions.assertEquals(
                messageUnregisteredProduct.getMessage(), "Produto não cadastrado!"
        );
    }

    @Test
    @DisplayName("Testa se retorna exception de produtos insuficiente")
    public void testExceptionMissingProduct () {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        Customer customer = createCustomer();
        Product product = createProduct();

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Mockito.when(batchStockRepository.findValidDateItems(Mockito.anyLong())).thenReturn(new ArrayList<>());

        Throwable messageMissingProduct = Assertions.assertThrows(MissingProductExceptions.class, () ->
                purchaseOrderService.createPurchaseOrder(purchaseOrderDTO));

        Assertions.assertEquals(
                messageMissingProduct.getMessage(), "Product insuficiente em estoque!"
        );

    }


}
