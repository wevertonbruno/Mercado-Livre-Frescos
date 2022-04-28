package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * @author Jefferson Botelho
 *
 */

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    @Test
    public void showProductsInOrdersTest() {

    }

    @Test
    @DisplayName("Verifica se o retorno de excecao esta correto em caso de id invalido")
    public void editExistentOrderExceptionTest() {

        // configuracao
        PurchaseOrderStatusDTO test = new PurchaseOrderStatusDTO();

        Mockito.when(purchaseOrderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> purchaseOrderService.editExistentOrder(1L, test));

        // verificacoes
        Assertions.assertEquals("Pedido nao encontrado", runtimeException.getMessage());
    }


    @Test
    @DisplayName("Verifica se o service altera o status corretamente")
    public void editExistentOrderOkTest() {

        PurchaseOrder order = createOrder();
        PurchaseOrderStatusDTO orderStatusTest = new PurchaseOrderStatusDTO(OrderStatus.OPENED);

        Mockito.when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        PurchaseOrderStatusDTO retorno1 = purchaseOrderService.editExistentOrder(1L, orderStatusTest);

        Assertions.assertEquals(retorno1.getStatus(), OrderStatus.OPENED);
    }


    private PurchaseOrder createOrder() {
        PurchaseOrder purchaseOrderTest = new PurchaseOrder();
        purchaseOrderTest.setId(1L);
        purchaseOrderTest.setOrderStatus(OrderStatus.SENT);

        return purchaseOrderTest;
    }
}


