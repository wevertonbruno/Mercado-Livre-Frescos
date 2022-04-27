package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.exceptions.MissingProductExceptions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UnregisteredUser;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PurchaseOrder createPurchaseOrder (PurchaseOrderDTO purchaseOrderDTO) {
        // lista de produtos do purchaseOrderDTO
        List<PurchaseOrderDTO.ProductItemDTO> productsPurchaseOrders = purchaseOrderDTO.getPurchaseOrder().getProducts();

        // salvando uma lista de produtos para fazer o purchaseOrderDTO
        List<PurchaseItem> purchaseItemList = new ArrayList<>();

        // se não houver usuário cadastrado ira retornar erro.
        Customer customer = customerRepository
                .findById(purchaseOrderDTO.getPurchaseOrder().getBuyerId())
                .orElseThrow(() -> new UnregisteredUser("Usuário nao cadastrado"));

        // percorrer a lista de produtos do purchaseOrderDTO
        for (PurchaseOrderDTO.ProductItemDTO productItemDTO : productsPurchaseOrders) {

            // verifica se o produto esta cadastrado e retorna um erro caso nao esteja
            Product prodRepository = productRepository
                    .findById(productItemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto nao cadastrado"));

            // procura no lote se existe o produto solicitado e com data de vencimento superior a solicitada
            List<BatchStock> prodInStock = batchStockRepository
                    .findValidDateItems(productItemDTO.getProductId());

            // variável que vai armazenar quantos produtos existem com as condicao passada
            int totalDeProdutos = 0;

            for (BatchStock p : prodInStock) {
                totalDeProdutos += p.getCurrentQuantity();
            }

            // valida se existe a quantidade de itens do PurchaseItems
            if (totalDeProdutos < productItemDTO.getQuantity()) {
                throw new MissingProductExceptions(String.format("%s insuficiente em estoque", prodRepository.getNome()));
            }

            // cria uma lista de purchaseitems para ser salvo no banco
            purchaseItemList.add(new PurchaseItem(null, prodRepository, null, productItemDTO.getQuantity()));
        }

        // criando o purchaseOrder para ser salvo no banco
        PurchaseOrder purchaseOrder = new PurchaseOrder(null, purchaseItemList, LocalDateTime.now(), LocalDateTime.now(), customer, OrderStatus.OPENED);
        // salva no banco o purchaseOrder
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        // adiciona o purchaseOrder em cada item
//        for (PurchaseItem p : purchaseOrder.getProducts()) {
//            p.setPurchaseOrder(purchaseOrder);
//        }

        //salva no banco as mudanas feitas
//        purchaseOrderRepository.save(purchaseOrder);

        return purchaseOrder;
    }

}
