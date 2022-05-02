package com.mercadolibre.grupo1.projetointegrador.services;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;

public interface PurchaseOrderService {
    PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
    PurchaseOrder showProductsInOrders(Long id); // ira exibir todos os produtos dentro do carrinho pelo id do carrinho
}
