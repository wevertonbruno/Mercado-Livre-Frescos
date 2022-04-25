package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adicionado service que sera responsavel pelas regras de negocio do controller
 *
 * @author  Jefferson Botelho
 * @since   2022-03-25
 *
 */

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public List<ProductDTO> allProductsList() {
        return null;

    }

    @Override
    public List<ProductDTO> listProductByStatus(PurchaseOrder orderStatus) {
        return null;
    }

    @Override
    public PurchaseOrder calcFinal(PurchaseOrder purchaseOrder) {
        return null;
    }

    @Override
    public List<PurchaseOrder> showProductsInOrders() {
        return null;
    }

    @Override
    public PurchaseOrder editExistentOrder(Long id, PurchaseOrderStatusDTO status) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).map(ordersMap -> {
            ordersMap.setOrderStatus(status.getStatus());

            return ordersMap;
        }).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        return purchaseOrderRepository.save(purchaseOrder);
    }


}
