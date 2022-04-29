package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredProducts;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.repositories.PurchaseOrderRepository;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public PurchaseOrder calcFinal(PurchaseOrder purchaseOrder) {
        return null;
    }


    /**
     * a funcao showProductsInOrders ira retornar todos os produtos em um carrinho pelo id do carrinho
     * a funcao editExistentOrder ira atualizar o status de um pedido
     *
     * @author  Jefferson Botelho
     * @since   2022-03-25
     *
     */
    @Override
    public PurchaseOrder showProductsInOrders(Long id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase nao encontrado!"));

        return order;
    }

    @Override
    public PurchaseOrderStatusDTO editExistentOrder(Long id, PurchaseOrderStatusDTO status) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).map(ordersMap -> {
            ordersMap.setOrderStatus(status.getStatus());

            return ordersMap;
        }).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));

        purchaseOrderRepository.save(purchaseOrder);

        return new PurchaseOrderStatusDTO(status.getStatus());
    }


/**
 * @author Ederson Rodrigues Araujo
 * service responsavel por criar um purchase order
 */

    @Transactional // so salva no banco se nao houver erro
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {

        // lista de produtos do purchaseOrderDTO
        List<PurchaseOrderDTO.ProductItemDTO> productsPurchaseOrders = purchaseOrderDTO.getPurchaseOrder().getProducts();

        // salvando uma lista de produtos para fazer o purchaseOrderDTO
        List<PurchaseItem> purchaseItemList = new ArrayList<>();

        // se não houver usuário cadastrado ira retornar erro.
        Customer customer = customerRepository
                .findById(purchaseOrderDTO.getPurchaseOrder().getBuyerId())
                .orElseThrow(() -> new UnregisteredUser("Usuário não cadastrado"));

        // percorrer a lista de produtos do purchaseOrderDTO
        for (PurchaseOrderDTO.ProductItemDTO productItemDTO : productsPurchaseOrders) {

            // verifica se o produto esta cadastrado e retorna um erro caso nao esteja
            Product prodRepository = productRepository
                    .findById(productItemDTO.getProductId())
                    .orElseThrow(() -> new UnregisteredProducts("Produto não cadastrado!"));
            /**
             * procura no lote se existe o produto solicitado com a data de vencimento superior
             * e retorna a soma de todos os produtos.
             */
            Double quantityProdInStock = batchStockRepository
                    .findValidDateItems(productItemDTO.getProductId());

            // valida se existe a quantidade de itens do PurchaseItems
            if (quantityProdInStock == null || quantityProdInStock < productItemDTO.getQuantity()) {
                throw new MissingProductExceptions(String.format("%s insuficiente em estoque!", prodRepository.getNome()));
            }

            // cria uma lista de purchaseitems para ser salvo no banco
            purchaseItemList.add(new PurchaseItem(null, prodRepository, null, productItemDTO.getQuantity()));
        }

        // criando o purchaseOrder para ser salvo no banco
        PurchaseOrder purchaseOrder = new PurchaseOrder(null, purchaseItemList, LocalDateTime.now(), LocalDateTime.now(), customer, OrderStatus.OPENED);

        // salva no banco o purchaseOrder
        PurchaseOrder purchaseOrderWithItemsList = purchaseOrderRepository.save(purchaseOrder);

        // vincula o purchaseOrder em cada item do purchaseOrder
        purchaseItemList.forEach(product -> product.setPurchaseOrder(purchaseOrderWithItemsList));

        return purchaseOrderWithItemsList;
    }


}
