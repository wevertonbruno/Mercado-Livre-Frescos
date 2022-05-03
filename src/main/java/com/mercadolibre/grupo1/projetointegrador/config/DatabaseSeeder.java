package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.OrderStatus;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * Classe responsável por popular o banco de dados com dados de teste.
 *
 * @author Grupo 1
 */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BatchStockRepository batchStockRepository;
    private final WarehouseRepository warehouseRepository;
    private final SectionRepository sectionRepository;
    private final InboundOrderRepository inboundOrderRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseItemRepository purchaseItemRepository;

    @Transactional
    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
        seedAgents();
        seedCustomer();
        seedProducts();
        seedWarehouse();
        seedSection();
        seedInboundOrder();
        seedBatchStock();
        seedProducts();
        seedPurchaseOrders();

        LOGGER.info("Seeding complete...");
    }

    private void seedRoles() {
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
        roleRepository.save(Role.builder().id(3L).name("ROLE_CUSTOMER").build());
    }

    private void seedSellers() {
        Role sellerRole = roleRepository.findById(2L).get();
        sellerRepository.save(Seller.builder().id(1L).username("seller1").password("123456").email("seller1@mercadolibre.com").roles(Set.of(sellerRole)).build());
        sellerRepository.save(Seller.builder().id(2L).username("seller2").password("123456").email("seller2@mercadolibre.com").roles(Set.of(sellerRole)).build());
    }

    private void seedAgents() {
        Role agentRole = roleRepository.findById(1L).get();
        agentRepository.save(Agent.builder().id(1L).username("agent1").password("123456").email("agent1@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent2").password("123456").email("agent2@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent3").password("123456").email("agent3@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent4").password("123456").email("agent4@mercadolibre.com").roles(Set.of(agentRole)).build());
    }


    private void seedCustomer() {
        Role customerRole = roleRepository.findById(3L).get();
        customerRepository.save(Customer.builder().username("customer1").password("123456").email("customer1@mercadolibre.com").roles(Set.of(customerRole)).build());
        customerRepository.save(Customer.builder().username("customer2").password("123456").email("customer2@mercadolibre.com").roles(Set.of(customerRole)).build());
    }

    private void seedProducts() {
        productRepository.save(Product.builder().id(1L).nome("Product1").volume(1D).price(BigDecimal.valueOf(100)).category(ProductCategory.FRESCO).build());
        productRepository.save(Product.builder().id(2L).nome("Product2").volume(2D).price(BigDecimal.valueOf(200)).category(ProductCategory.CONGELADO).build());
        productRepository.save(Product.builder().id(3L).nome("Action Figure Mokey D Luffy").volume(3D).price(BigDecimal.valueOf(300)).category(ProductCategory.REFRIGERADO).build());
        productRepository.save(Product.builder().id(4L).nome("Product4").volume(4D).price(BigDecimal.valueOf(400)).category(ProductCategory.FRESCO).build());
        productRepository.save(Product.builder().id(5L).nome("Product5").volume(5D).price(BigDecimal.valueOf(500)).category(ProductCategory.REFRIGERADO).build());
    }

    private void seedPurchaseOrders() {


        PurchaseOrder save = purchaseOrderRepository.save(PurchaseOrder.builder()
                .id(1L)
                .createdDate(LocalDateTime.parse("2015-08-04T10:11:30"))                                // insere DateTime por string
                .updatedDate(LocalDateTime.of(2022, 4, 26, 10, 0))  // insere DateTime por int
                .orderStatus(OrderStatus.OPENED)
                .build());

        List<PurchaseItem> purchaseItemList = Arrays.asList(
                PurchaseItem.builder()
                        .purchaseOrder(save)
                        .id(1L).product(productRepository.getById(1L)).quantity(1)
                        .build(),
                PurchaseItem.builder()
                        .purchaseOrder(save)
                        .id(2L).product(productRepository.getById(2L)).quantity(2)
                        .build(),
                PurchaseItem.builder()
                        .purchaseOrder(save)
                        .id(3L).product(productRepository.getById(3L)).quantity(4)
                        .build());

        purchaseItemRepository.saveAll(purchaseItemList);


    }

    private void seedWarehouse() {
        warehouseRepository.save(Warehouse.builder().id(1L).address("Address A").name("WH1").build());
    }

    private void seedSection() {
        Warehouse warehouse = warehouseRepository.findById(1L).get();
        sectionRepository.save(Section.builder().id(1L).description("description").capacity(500.).warehouse(warehouse).build());
    }

    private void seedInboundOrder() {
        Section section = sectionRepository.getById(1L);
        inboundOrderRepository.save(InboundOrder.builder().id(1L).orderDate(LocalDate.now()).section(section).build());
    }

    private void seedBatchStock() {
        Product product1 = productRepository.getById(1L);
        Product product2 = productRepository.getById(2L);
        InboundOrder inboundOrder = inboundOrderRepository.findById(1L).get();
        batchStockRepository.save(BatchStock.builder()
                .id(1L)
                .product(product1)
                .currentTemperature(20F)
                .minimumTemperature(10F)
                .initialQuantity(20)
                .currentQuantity(20)
                .manufacturingDateTime(LocalDateTime.now())
                .dueDate(LocalDate.parse("2023-01-01"))
                .build());
        batchStockRepository.save(BatchStock.builder().id(2L).product(product2).currentTemperature(20F).minimumTemperature(10F).initialQuantity(20).currentQuantity(20).manufacturingDateTime(LocalDateTime.now()).dueDate(LocalDate.parse("2022-05-01")).build());

    }
}