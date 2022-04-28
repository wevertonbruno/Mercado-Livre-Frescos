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
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

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
    private final CustomerRepository customerRepository;
    private final WarehouseRepository warehouseRepository;
    private final SectionRepository sectionRepository;
//    private final BatchStockRepository batchStockRepository;

    @Transactional
    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
        seedCustomer();
        seedAgents();
        seedProducts();
        seedPurchaseOrders();
        seedPurchaseItems();
        seedWarehouses();

        LOGGER.info("Seeding complete...");
    }

    private void seedPurchaseItems() {
        Product oldProduct = productRepository.findById(1L).get();
        PurchaseOrder oldPurchaseOrder = purchaseOrderRepository.findById(1L).get();
        purchaseItemRepository.save(PurchaseItem.builder().id(1L).product(oldProduct).purchaseOrder(oldPurchaseOrder).quantity(2).build());
    }

    private void seedPurchaseOrders() {

        purchaseOrderRepository.save(PurchaseOrder.builder().id(1L).orderStatus(OrderStatus.SENT).build());
        purchaseOrderRepository.save(PurchaseOrder.builder().id(2L).orderStatus(OrderStatus.CLOSED).build());
        purchaseOrderRepository.save(PurchaseOrder.builder().id(3L).orderStatus(OrderStatus.OPENED).build());
        purchaseOrderRepository.save(PurchaseOrder.builder().id(4L).orderStatus(OrderStatus.PREPARING).build());
        purchaseOrderRepository.save(PurchaseOrder.builder().id(5L).orderStatus(OrderStatus.OPENED).build());
    }

    private void seedRoles(){
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
        roleRepository.save(Role.builder().id(3L).name("ROLE_CUSTOMER").build());
    }

        private void seedCustomer() {
        Role customerRole = roleRepository.findById(3L).get();
        customerRepository.save(Customer.builder().id(1L).username("customer1").password("123456").email("custumer1@mercadolibre.com").roles(Set.of(customerRole)).build());
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

    private void seedWarehouses(){
        Warehouse w1 = warehouseRepository.save(Warehouse.builder().id(1L).name("SP-SP").address("00000-000").build());
        sectionRepository.save(Section.builder().id(1L).capacity(100.0).category(ProductCategory.FRESCO).warehouse(w1).description("Sessao de frescos").build());
        sectionRepository.save(Section.builder().id(2L).capacity(100.0).category(ProductCategory.CONGELADO).warehouse(w1).description("Sessao de congelados").build());
        sectionRepository.save(Section.builder().id(3L).capacity(100.0).category(ProductCategory.REFRIGERADO).warehouse(w1).description("Sessao de refrigerados").build());
    }

    private void seedProducts() {
        Seller s1 = sellerRepository.findById(1L).get();
        Seller s2 = sellerRepository.findById(2L).get();
        productRepository.save(Product.builder().id(1L).seller(s1).category(ProductCategory.CONGELADO).price(BigDecimal.TEN).nome("peixe").volume(10.0).build());
        productRepository.save(Product.builder().id(2L).seller(s2).category(ProductCategory.FRESCO).price(BigDecimal.TEN).nome("sardinha").volume(5.0).build());
        productRepository.save(Product.builder().id(3L).seller(s1).category(ProductCategory.REFRIGERADO).price(BigDecimal.TEN).nome("carne").volume(15.0).build());
    }

}