package com.mercadolibre.grupo1.projetointegrador.services;

//<<<<<<< HEAD
//import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
//import com.mercadolibre.grupo1.projetointegrador.entities.*;
//import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
//import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
//import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredProducts;
//import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
//import com.mercadolibre.grupo1.projetointegrador.repositories.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author Ederson Rodrigues Araujo
// * service responsavel por criar um purchase order
// */
//
//@Service
//public class PurchaseOrderService {
//
//    @Autowired
//    private PurchaseOrderRepository purchaseOrderRepository;
//
//    @Autowired
//    private BatchStockRepository batchStockRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Transactional // so salva no banco se nao houver erro
//    public PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
//
//        // lista de produtos do purchaseOrderDTO
//        List<PurchaseOrderDTO.ProductItemDTO> productsPurchaseOrders = purchaseOrderDTO.getPurchaseOrder().getProducts();
//
//        // salvando uma lista de produtos para fazer o purchaseOrderDTO
//        List<PurchaseItem> purchaseItemList = new ArrayList<>();
//
//        // se não houver usuário cadastrado ira retornar erro.
//        Customer customer = customerRepository
//                .findById(purchaseOrderDTO.getPurchaseOrder().getBuyerId())
//                .orElseThrow(() -> new UnregisteredUser("Usuário não cadastrado"));
//
//        // percorrer a lista de produtos do purchaseOrderDTO
//        for (PurchaseOrderDTO.ProductItemDTO productItemDTO : productsPurchaseOrders) {
//
//            // verifica se o produto esta cadastrado e retorna um erro caso nao esteja
//            Product prodRepository = productRepository
//                    .findById(productItemDTO.getProductId())
//                    .orElseThrow(() -> new UnregisteredProducts("Produto não cadastrado!"));
//
//            /**
//             * procura no lote se existe o produto solicitado com a data de vencimento superior
//             * e retorna a soma de todos os produtos.
//             */
//            Double quantityProdInStock = batchStockRepository
//                    .findValidDateItems(productItemDTO.getProductId());
//
//            // valida se existe a quantidade de itens do PurchaseItems
//            if (quantityProdInStock == null || quantityProdInStock < productItemDTO.getQuantity()) {
//                throw new MissingProductExceptions(String.format("%s insuficiente em estoque!", prodRepository.getNome()));
//            }
//
//            // cria uma lista de purchaseitems para ser salvo no banco
//            purchaseItemList.add(new PurchaseItem(null, prodRepository, null, productItemDTO.getQuantity()));
//        }
//
//        // criando o purchaseOrder para ser salvo no banco
//        PurchaseOrder purchaseOrder = new PurchaseOrder(null, purchaseItemList, LocalDateTime.now(), LocalDateTime.now(), customer, OrderStatus.OPENED);
//
//        // salva no banco o purchaseOrder
//        PurchaseOrder purchaseOrderWithItemsList = purchaseOrderRepository.save(purchaseOrder);
//
//        // vincula o purchaseOrder em cada item do purchaseOrder
//        purchaseItemList.forEach(product -> product.setPurchaseOrder(purchaseOrderWithItemsList));
//
//        return purchaseOrderWithItemsList;
//    }

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {

    List<ProductDTO> allProductsList();
    List<ProductDTO> listProductByStatus(PurchaseOrder orderStatus);
    PurchaseOrder calcFinal(PurchaseOrder purchaseOrder);
    List<PurchaseOrder> showProductsInOrders();
    PurchaseOrder editExistentOrder(Long id, PurchaseOrder purchaseOrder);
}
