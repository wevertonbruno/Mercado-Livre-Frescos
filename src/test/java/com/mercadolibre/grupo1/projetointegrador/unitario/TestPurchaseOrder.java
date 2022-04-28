package com.mercadolibre.grupo1.projetointegrador.unitario;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Customer;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import com.mercadolibre.grupo1.projetointegrador.repositories.CustomerRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SectionRepository;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestPurchaseOrder {

    @Mock
    PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    PurchaseOrderService purchaseOrderService;

    private PurchaseOrderDTO purchaseOrder() {
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

    private Customer customer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setPassword("123456");
        customer.setCpf("111.111.111-01");
        customer.setEmail("ederson@mercadolivre.com");

        return customer;
    }

    @Test
    @DisplayName("Testando se retorna a mensagem de error: Usuário não cadastrado")
    public void testandoExcepitionUregistered() {

        // Configuração
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrder();

        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Action
        Throwable messageUnregisteredUser = Assertions.assertThrows(UnregisteredUser.class, () ->
                purchaseOrderService.createPurchaseOrder(purchaseOrderDTO));

        // Verificação
        Assertions.assertEquals(
                messageUnregisteredUser.getMessage(), "Usuário não cadastrado"
        );
    }


}
