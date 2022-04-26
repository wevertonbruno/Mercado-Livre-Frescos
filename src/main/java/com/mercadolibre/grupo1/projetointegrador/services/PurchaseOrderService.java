package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {

    List<ProductDTO> allProductsList();
    List<ProductDTO> listProductByStatus(PurchaseOrder orderStatus);
    PurchaseOrder calcFinal(PurchaseOrder purchaseOrder);
    PurchaseOrder showProductsInOrders(Long id); // ira exibir todos os produtos dentro do carrinho pelo id do carrinho
    PurchaseOrder editExistentOrder(Long id, PurchaseOrder purchaseOrderStatus);
}
