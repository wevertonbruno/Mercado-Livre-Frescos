package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;


public interface PurchaseOrderService {

    PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
    PurchaseOrder calcFinal(PurchaseOrder purchaseOrder);
    PurchaseOrder showProductsInOrders(Long id); // ira exibir todos os produtos dentro do carrinho pelo id do carrinho
    PurchaseOrderStatusDTO editExistentOrder(Long id, PurchaseOrderStatusDTO purchaseOrderStatus);
}
