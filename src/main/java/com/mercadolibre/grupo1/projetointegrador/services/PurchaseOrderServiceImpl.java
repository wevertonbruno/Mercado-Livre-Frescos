package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    /**
     *
     * @author  Jefferson Botelho
     * @since   2022-03-25
     *
     */

    // a funcao showProductsInOrders ira retornar todos os produtos em um carrinho pelo id do carrinho
    @Override
    public PurchaseOrder showProductsInOrders(Long id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        return order;
    }

    // a funcao editExistentOrder ira atualizar o status de um pedido
    @Override
    public PurchaseOrderStatusDTO editExistentOrder(Long id, PurchaseOrderStatusDTO status) {

        // reutilizando o metodo showProductsInOrders, caso o id nao seja encontrado sera retornado a Excecao do metodo
        PurchaseOrder findId = showProductsInOrders(id);
        findId.setOrderStatus(status.getStatus());
        purchaseOrderRepository.save(findId);

        return new PurchaseOrderStatusDTO(status.getStatus());
    }


}
