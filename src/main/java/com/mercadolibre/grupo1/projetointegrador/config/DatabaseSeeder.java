package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
        seedAgents();
        seedCustomer();
        seedSection();
        seedInboundOrder();
        seedBatchStock();
        seedWarehouses();
        seedProducts();

        LOGGER.info("Seeding complete...");
    }

    private void seedRoles() {
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
        roleRepository.save(Role.builder().id(3L).name("ROLE_CUSTOMER").build());
    }

    private void seedSellers() {
        Role sellerRole = roleRepository.findById(2L).get();

        AuthenticableUser user1 = AuthenticableUser.builder().id(1L).username("seller1").password("123456").email(
                "seller1@mercadolibre.com").roles(Set.of(sellerRole)).build();
        AuthenticableUser user2 = AuthenticableUser.builder().id(2L).username("seller2").password("123456").email(
                "seller2@mercadolibre.com").roles(Set.of(sellerRole)).build();

        sellerRepository.save(new Seller(user1));
        sellerRepository.save(new Seller(user2));
    }

    private void seedAgents() {
        Role agentRole = roleRepository.findById(1L).get();

        AuthenticableUser user3 = AuthenticableUser.builder().id(1L).username("agent1").password("123456").email(
                "agent1@mercadolibre.com").roles(Set.of(agentRole)).build();
        AuthenticableUser user4 = AuthenticableUser.builder().id(2L).username("agent2").password("123456").email(
                "agent2@mercadolibre.com").roles(Set.of(agentRole)).build();
        AuthenticableUser user5 = AuthenticableUser.builder().id(2L).username("agent3").password("123456").email(
                "agent3@mercadolibre.com").roles(Set.of(agentRole)).build();
        AuthenticableUser user6 = AuthenticableUser.builder().id(2L).username("agent4").password("123456").email(
                "agent4@mercadolibre.com").roles(Set.of(agentRole)).build();

        agentRepository.save(new Agent(user3));
        agentRepository.save(new Agent(user4));
        agentRepository.save(new Agent(user5));
        agentRepository.save(new Agent(user6));
    }

    private void seedCustomer() {
        Role customerRole = roleRepository.findById(3L).get();
        customerRepository.save(Customer.builder().username("customer1").password("123456").email("customer1" +
                "@mercadolibre.com").roles(Set.of(customerRole)).build());
        customerRepository.save(Customer.builder().username("customer2").password("123456").email("customer2" +
                "@mercadolibre.com").roles(Set.of(customerRole)).build());
    }

    private void seedSection() {
        Warehouse warehouse1 = warehouseRepository.findById(1L).get();
        Warehouse warehouse2 = warehouseRepository.findById(2L).get();
        sectionRepository.save(Section.builder().id(1L).description("description").capacity(500.).warehouse(warehouse1).build());
        sectionRepository.save(Section.builder().id(2L).description("description").capacity(600.).warehouse(warehouse2).build());

    }

    private void seedInboundOrder() {
        Section section1 = sectionRepository.getById(1L);
        Section section2 = sectionRepository.getById(2L);
        inboundOrderRepository.save(InboundOrder.builder().id(1L).orderDate(LocalDate.now()).section(section1).build());
        inboundOrderRepository.save(InboundOrder.builder().id(2L).orderDate(LocalDate.now()).section(section2).build());

    }

    private void seedBatchStock() {
        Product product1 = productRepository.getById(1L);
        Product product2 = productRepository.getById(2L);
        InboundOrder inboundOrder = inboundOrderRepository.findById(1L).get();
        batchStockRepository.save(BatchStock.builder().id(1L).product(product1).currentTemperature(20F).minimumTemperature(10F).initialQuantity(20).currentQuantity(20).manufacturingDateTime(LocalDateTime.now()).dueDate(LocalDate.parse("2023-01-01")).inboundOrder(inboundOrder).build());
        batchStockRepository.save(BatchStock.builder().id(2L).product(product2).currentTemperature(20F).minimumTemperature(10F).initialQuantity(20).currentQuantity(20).manufacturingDateTime(LocalDateTime.now()).dueDate(LocalDate.parse("2022-05-01")).inboundOrder(inboundOrder).build());
        batchStockRepository.save(BatchStock.builder().id(3L).product(product1).currentTemperature(20F).minimumTemperature(10F).initialQuantity(20).currentQuantity(20).manufacturingDateTime(LocalDateTime.now()).dueDate(LocalDate.parse("2024-01-01")).inboundOrder(inboundOrder).build());
    }

    private void seedWarehouses() {
        Warehouse w1 =
                warehouseRepository.save(Warehouse.builder().id(1L).name("SP-SP").address("00000-000").build());
        sectionRepository.save(Section.builder().id(1L).capacity(500.0).category(ProductCategory.FRESCO).warehouse(w1).description("Sessao de frescos").build());
        sectionRepository.save(Section.builder().id(2L).capacity(500.0).category(ProductCategory.CONGELADO).warehouse(w1).description("Sessao de congelados").build());
        sectionRepository.save(Section.builder().id(3L).capacity(500.0).category(ProductCategory.REFRIGERADO).warehouse(w1).description("Sessao de refrigerados").build());

    }

    private void seedProducts() {
        Seller s1 = sellerRepository.findById(1L).get();
        Seller s2 = sellerRepository.findById(2L).get();
        productRepository.save(Product.builder().id(1L).seller(s1).category(ProductCategory.CONGELADO).price(BigDecimal.TEN).name("peixe").volume(10.0).build());
        productRepository.save(Product.builder().id(2L).seller(s2).category(ProductCategory.FRESCO).price(BigDecimal.TEN).name("sardinha").volume(5.0).build());
        productRepository.save(Product.builder().id(3L).seller(s1).category(ProductCategory.REFRIGERADO).price(BigDecimal.TEN).name("carne").volume(15.0).build());
    }
}

