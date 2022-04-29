package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseItem;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;


    public List<ProductDTO> allProductsList() {
        return null;

    }

    public List<ProductDTO> listProductByStatus(PurchaseOrder orderStatus) {
        return null;
    }


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
    /*
    * @author Gabriel Essenio
      funcao editExistentOrder ira atualizar o status de um pedido e diminir quantidade de stock de acordo com cada compra
    */
    @Transactional
    public PurchaseOrder editExistentOrder(Long id) {

        PurchaseOrder purchaseOrder = showProductsInOrders(id);
        purchaseOrder.setOrderStatus(OrderStatus.CLOSED);
        List<PurchaseItem> purchaseItems = purchaseOrder.getProducts();
        purchaseItems.stream().forEach(prod -> {
        Integer qntyAux = prod.getQuantity();
        List<BatchStock> batchStock = batchStockRepository.findStockByProductId(prod.getProduct().getId());
        for (BatchStock stock: batchStock){
            if(qntyAux <= 0){
                break;
            }
            if(prod.getQuantity() < stock.getCurrentQuantity()){
                stock.setCurrentQuantity(stock.getCurrentQuantity() - prod.getQuantity());
                }
            else{
                qntyAux -= stock.getCurrentQuantity();
                stock.setCurrentQuantity(0);
            }
            batchStockRepository.save(stock);
        }
        });
        purchaseOrderRepository.save(purchaseOrder);
        return purchaseOrder;
    }
}
