package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Jefferson Botelho, Gabriel Essenio
 */

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private BatchStockRepository batchStockRepository;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    /**
     * @author Jefferson Botelho
     */
    @Test
    @DisplayName("Verifica se o service esta retornando os produtos do carrinho")
    public void showProductsInOrdersIsOkTest() {
        Customer customer1 = createCustomer(1L);

        PurchaseOrder test = createOrder(customer1);
        Mockito.when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(test));
        PurchaseOrder retorno = purchaseOrderService.showProductsInOrders(1L);
        Assertions.assertEquals(2, retorno.getProducts().size());
        Assertions.assertEquals(LocalDateTime.parse("2015-08-04T10:11:30"), retorno.getCreatedDate());
        Assertions.assertEquals(LocalDateTime.parse("2022-04-26T10:00"), retorno.getUpdatedDate());
    }

    @Test
    @DisplayName("Verifica se o retorno de excecao esta correto em caso de id invalido")
    public void editExistentOrderExceptionTest() {
        Customer customer1 = createCustomer(1L);
        Mockito.when(purchaseOrderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        // action
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> purchaseOrderService.editExistentOrder(1L, customer1));

        // verificacoes
        Assertions.assertEquals("Pedido nao encontrado", runtimeException.getMessage());
    }

    /**
     * @author Gabriel Essenio, Jefferson Botelho
     * teste que verifica se o status é alterado corretamente e se ao finalizar a compra é debitado do stock os
     */
    @Test
    @DisplayName("Verifica se o service altera o status corretamente. E Verifica também se e debitado no stocks a quantidade de produtos que tem na compra")
    public void editExistentOrderOkTest() {

        Customer customer1 = createCustomer(1L);
        Customer customer2 = createCustomer(2L);
        PurchaseOrder order = createOrder(customer1);
        Product newProduct1 = createProduct(1L, "Gelo", 20D, BigDecimal.valueOf(30), ProductCategory.FRESCO);
        Product newProduct2 = createProduct(2L, "Peixe", 10D, BigDecimal.valueOf(20), ProductCategory.REFRIGERADO);
        Product newProduct3 = createProduct(3L, "Leite", 10D, BigDecimal.valueOf(20), ProductCategory.REFRIGERADO);
        BatchStock batchStock1 = createBatchStock(1L, newProduct1, 14.0F, 5.0F, 32, 24, LocalDateTime.parse("2022-04-30T19:34:50.63"), LocalDate.parse("2022-12-25"));
        BatchStock batchStock2 = createBatchStock(2L, newProduct2, 14.0F, 5.0F, 32, 21, LocalDateTime.parse("2022-04-30T19:34:50.63"), LocalDate.parse("2022-12-25"));
        BatchStock batchStock3 = createBatchStock(3L, newProduct3, 14.0F, 5.0F, 32, 33, LocalDateTime.parse("2022-04-30T19:34:50.63"), LocalDate.parse("2022-12-25"));
        BatchStock batchStock4 = createBatchStock(4L, newProduct3, 14.0F, 5.0F, 32, 34, LocalDateTime.parse("2022-04-30T19:34:50.63"), LocalDate.parse("2022-12-25"));
        List<BatchStock> listBatchStock1 = createListBatchStock(batchStock1, batchStock3);
        List<BatchStock> listBatchStock2 = createListBatchStock(batchStock2, batchStock4);

        Mockito.when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(order));
        Mockito.when(batchStockRepository.findStockByProductId(1L)).thenReturn(listBatchStock1);
        Mockito.when(batchStockRepository.findStockByProductId(2L)).thenReturn(listBatchStock2);

        PurchaseOrder editedOrder = purchaseOrderService.editExistentOrder(1L, customer1);

        Assertions.assertEquals(editedOrder.getOrderStatus(), OrderStatus.CLOSED);
        Assertions.assertEquals(listBatchStock1.get(0).getCurrentQuantity(), 19);
        Assertions.assertEquals(listBatchStock2.get(0).getCurrentQuantity(), 7);
        Assertions.assertEquals(order.getCustomer().getId(), 1L);
        Assertions.assertThrows(ListIsEmptyException.class, ()-> purchaseOrderService.editExistentOrder(1L, customer2));
    }

    /**
     * @author Gabriel Essenio, Jefferson Botelho
     * Cria um Customer com Cpf, Role e ID
     */
    private Customer createCustomer(Long id) {
        Customer customer = new Customer();
        customer.setCpf("023.123.429-09");
        customer.setId(id);
        Role roleCustomer = new Role(1L, "Customer");
        customer.setRoles(Set.of(roleCustomer));
        return customer;
    }

    /**
     * @author Gabriel Essenio, Jefferson Botelho
     * Cria lista de Purchase Item  com produto e quantidade
     */
    private PurchaseOrder createOrder(Customer customer) {
        PurchaseOrder purchaseOrderTest = new PurchaseOrder();
        List<PurchaseItem> listPurchaseItem = createListPurchaseItem();
        purchaseOrderTest.setId(1L);
        purchaseOrderTest.setOrderStatus(OrderStatus.OPENED);
        purchaseOrderTest.setProducts(listPurchaseItem);
        purchaseOrderTest.setCustomer(customer);
        purchaseOrderTest.setCreatedDate(LocalDateTime.parse("2015-08-04T10:11:30"));
        purchaseOrderTest.setUpdatedDate(LocalDateTime.of(2022, 4, 26, 10, 0));

        return purchaseOrderTest;
    }

    private List<PurchaseItem> createListPurchaseItem() {
        PurchaseItem item1 = new PurchaseItem();
        Product newProduct1 = createProduct(1L, "Gelo", 20D, BigDecimal.valueOf(30), ProductCategory.FRESCO);
        item1.setProduct(newProduct1);
        item1.setQuantity(5);

        PurchaseItem item2 = new PurchaseItem();
        Product newProduct2 = createProduct(2L, "Peixe", 10D, BigDecimal.valueOf(20), ProductCategory.REFRIGERADO);
        item2.setProduct(newProduct2);
        item2.setQuantity(14);

        return Arrays.asList(item1, item2);
    }


    // cria um produto com dados pelo paramentro
    private Product createProduct(Long idProd, String nomeProd, Double volume, BigDecimal price, ProductCategory category) {
        Product product1 = new Product();
        product1.setId(idProd);
        product1.setName(nomeProd);
        product1.setVolume(volume);
        product1.setPrice(price);
        product1.setCategory(category);

        return product1;
    }

    //  Cria uma lista d Stock em Lote com dados ficticios
    private BatchStock createBatchStock(Long id, Product newProduct, Float temperature, Float minTemperature, Integer initialQuantity, Integer currentQuantity, LocalDateTime manufacturingTime, LocalDate dueDate) {

        BatchStock batchStock1 = new BatchStock();
        batchStock1.setId(id);
        batchStock1.setProduct(newProduct);
        batchStock1.setCurrentTemperature(temperature);
        batchStock1.setMinimumTemperature(minTemperature);
        batchStock1.setInitialQuantity(initialQuantity);
        batchStock1.setCurrentQuantity(currentQuantity);
        batchStock1.setManufacturingDateTime(manufacturingTime);
        batchStock1.setDueDate(dueDate);
        return batchStock1;
    }

    // Cria uma lista de BatchStock passada por parametro

    private List<BatchStock> createListBatchStock(BatchStock batchStock1, BatchStock batchStock2) {
        return Arrays.asList(batchStock1, batchStock2);
    }

}


