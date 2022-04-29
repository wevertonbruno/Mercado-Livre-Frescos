package com.mercadolibre.grupo1.projetointegrador.config;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * Classe responsável por popular o banco de dados com dados de teste.
 * @author Grupo 1
 */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseItemRepository purchaseItemRepository;

    public void seed() {
        LOGGER.info("Seeding database...");
        seedRoles();
        seedSellers();
        seedAgents();
        seedProducts();
        seedPurchaseOrders();

        LOGGER.info("Seeding complete...");
    }


    private void seedProducts() {
        Seller s1 = sellerRepository.findById(1L).get();
        Seller s2 = sellerRepository.findById(2L).get();
        productRepository.save(Product.builder().id(1L).seller(s1).nome("Product1").volume(1D).price(BigDecimal.valueOf(100)).category(ProductCategory.FRESCO).build());
        productRepository.save(Product.builder().id(2L).seller(s2).nome("Product2").volume(2D).price(BigDecimal.valueOf(200)).category(ProductCategory.CONGELADO).build());
        productRepository.save(Product.builder().id(3L).seller(s1).nome("Action Figure Mokey D Luffy").volume(3D).price(BigDecimal.valueOf(300)).category(ProductCategory.REFRIGERADO).build());
        productRepository.save(Product.builder().id(4L).nome("Product4").volume(4D).price(BigDecimal.valueOf(400)).category(ProductCategory.FRESCO).build());
        productRepository.save(Product.builder().id(5L).nome("Product5").volume(5D).price(BigDecimal.valueOf(500)).category(ProductCategory.REFRIGERADO).build());
    }

    private void seedPurchaseOrders() {


        PurchaseOrder product = purchaseOrderRepository.save(PurchaseOrder.builder()
                .id(1L)
                .createdDate(LocalDateTime.parse("2015-08-04T10:11:30"))                                // insere DateTime por string
                .updatedDate(LocalDateTime.of(2022, 4, 26, 10, 0))  // insere DateTime por int
                .orderStatus(OrderStatus.OPENED)
                .build());

        List<PurchaseItem> purchaseItemList = Arrays.asList(
                PurchaseItem.builder()
                    .purchaseOrder(product)
                    .id(1L).product(productRepository.getById(1L)).quantity(1)
                    .build(),
                PurchaseItem.builder()
                        .purchaseOrder(product)
                        .id(2L).product(productRepository.getById(2L)).quantity(2)
                        .build(),
                PurchaseItem.builder()
                        .purchaseOrder(product)
                        .id(3L).product(productRepository.getById(3L)).quantity(4)
                        .build());

        purchaseItemRepository.saveAll(purchaseItemList);

    }

    private void seedRoles(){
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
    }

    private void seedSellers(){
        Role sellerRole = roleRepository.findById(2L).get();
        sellerRepository.save(Seller.builder().id(1L).username("seller1").password("123456").email("seller1@mercadolibre.com").roles(Set.of(sellerRole)).build());
        sellerRepository.save(Seller.builder().id(2L).username("seller2").password("123456").email("seller2@mercadolibre.com").roles(Set.of(sellerRole)).build());
    }

    private void seedAgents(){
        Role agentRole = roleRepository.findById(1L).get();
        agentRepository.save(Agent.builder().id(1L).username("agent1").password("123456").email("agent1@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent2").password("123456").email("agent2@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent3").password("123456").email("agent3@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent4").password("123456").email("agent4@mercadolibre.com").roles(Set.of(agentRole)).build());
    }

}
