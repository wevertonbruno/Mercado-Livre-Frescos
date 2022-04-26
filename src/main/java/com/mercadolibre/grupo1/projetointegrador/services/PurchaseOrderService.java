package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

    public BigDecimal createPurchaseOrder (PurchaseOrderDTO purchaseOrderDTO) {
        // lista de produtos do purchaseOrderDTO
        List<PurchaseOrderDTO.ProductItemDTO> productsPurchaseOrders = purchaseOrderDTO.getProducts();

        // salvando uma lista de produtos para fazer o purchaseOrderDTO
        List<PurchaseItem> purchaseItemList = new ArrayList<>();

        // se não houver usuário cadastrado ira retornar erro.
        Customer customer = customerRepository
                .findById(purchaseOrderDTO.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Usuário nao cadastrado"));


        // percorrer a lista de produtos do purchaseOrderDTO
        for (PurchaseOrderDTO.ProductItemDTO productItemDTO : productsPurchaseOrders) {

            // verifica se o produto esta cadastrado e retorna um erro caso nao esteja
            Product prodRepository = productRepository
                    .findById(productItemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto nao cadastrado"));

            // procura no lote se existe o produto solicitado
            List<BatchStock> prodInStock = batchStockRepository
                    .findByProduct(prodRepository);

            // variável que vai armazenar quantos produtos existem com as condicao passada
            int totalDeProdutos = 0;

            // percorre a lista de produtos encontrados no armazem
            for (BatchStock productInStock : prodInStock) {

                // dias para se vencer do produto
                Period leftTime = Period.between(productInStock.getDueDate(), LocalDate.now());

                // validar se o produto tem mais 21 dias de validade
                if (leftTime.getDays() > 21) {
                    totalDeProdutos += productInStock.getCurrentQuantity();
                }
            }

            // valida se existe a quantidade de itens do PurchaseItems
            if (totalDeProdutos < productItemDTO.getQuantity()) {
                throw new RuntimeException("produtos insuficiente em estoque");
            }

            // cria uma lista de purchaseitems para ser salvo no banco
            purchaseItemList.add(new PurchaseItem(null, prodRepository, null, productItemDTO.getQuantity()));
        }

        // criando o purchaseOrder para ser salvo no banco
        PurchaseOrder purchaseOrder = new PurchaseOrder(null, purchaseItemList, LocalDateTime.now(), LocalDateTime.now(), customer, OrderStatus.OPENED);
        // salva no banco o purchaseOrder
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        // adiciona o purchaseOrder em cada item
        for (PurchaseItem p : purchaseOrder.getProducts()) {
            p.setPurchaseOrder(purchaseOrder);
        }

        //salva no banco as mudanas feitas
        purchaseOrderRepository.save(purchaseOrder);

        return purchaseOrder.totalPrice();
    }

}
