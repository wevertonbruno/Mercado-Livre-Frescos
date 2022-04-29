package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseItem;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * @author Jefferson Botelho
 *
 */

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @Mock private PurchaseOrderRepository purchaseOrderRepository;


    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    @Test
    @DisplayName("Verifica se o service esta retornando os produtos do carrinho")
    public void showProductsInOrdersIsOkTest() {

        PurchaseOrder test = createOrder();

        Mockito.when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(test));

        PurchaseOrder retorno = purchaseOrderService.showProductsInOrders(1L);

        Assertions.assertEquals(2, retorno.getProducts().size());
        Assertions.assertEquals(LocalDateTime.parse("2015-08-04T10:11:30"), retorno.getCreatedDate());
        Assertions.assertEquals(LocalDateTime.parse("2022-04-26T10:00"), retorno.getUpdatedDate());

    }

    @Test
    @DisplayName("Verifica se o retorno de excecao esta correto em caso de id invalido")
    public void editExistentOrderExceptionTest() {

        // configuracao
        PurchaseOrderStatusDTO dtoTest = new PurchaseOrderStatusDTO();

        Mockito.when(purchaseOrderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        RuntimeException runtimeException1 = Assertions.assertThrows(RuntimeException.class, () -> purchaseOrderService.editExistentOrder(1L, dtoTest));

        // verificacoes
        Assertions.assertEquals("Pedido nao encontrado", runtimeException1.getMessage());
    }


    @Test
    @DisplayName("Verifica se o service altera o status corretamente")
    public void editExistentOrderIsOkTest() {

        PurchaseOrder order = createOrder();
        PurchaseOrderStatusDTO orderStatusTest = new PurchaseOrderStatusDTO(OrderStatus.OPENED);

        Mockito.when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        PurchaseOrderStatusDTO retorno1 = purchaseOrderService.editExistentOrder(1L, orderStatusTest);

        Assertions.assertEquals(retorno1.getStatus(), OrderStatus.OPENED);
    }


    private PurchaseOrder createOrder() {

        PurchaseOrder purchaseOrderTest = PurchaseOrder.builder()
                .id(1L)
                .createdDate(LocalDateTime.parse("2015-08-04T10:11:30"))
                .updatedDate(LocalDateTime.of(2022, 4, 26, 10, 0))
                .orderStatus(OrderStatus.SENT)
                .build();

        List<PurchaseItem> purchaseItemList = Arrays.asList(
                PurchaseItem.builder()
                        .id(1L).quantity(1)
                        .build(),
                PurchaseItem.builder()
                        .id(3L).quantity(4)
                        .build());

        purchaseOrderTest.setProducts(purchaseItemList);

        return purchaseOrderTest;
    }

}


