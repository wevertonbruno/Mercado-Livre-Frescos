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
import java.util.List;
import java.util.Optional;
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
        seedProducts();
        seedWarehouse();
        seedSection();
        seedInboundOrder();
        seedBatchStock();

        LOGGER.info("Seeding complete...");
    }

    private void seedRoles(){
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
        roleRepository.save(Role.builder().id(3L).name("ROLE_CUSTOMER").build());
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

    private void seedCustomer() {
        Role customerRole = roleRepository.findById(3L).get();
        customerRepository.save(Customer.builder().username("customer1").password("123456").email("customer1@mercadolibre.com").roles(Set.of(customerRole)).build());
        customerRepository.save(Customer.builder().username("customer2").password("123456").email("customer2@mercadolibre.com").roles(Set.of(customerRole)).build());
    }

    private void seedProducts() {
        productRepository.save(Product.builder().id(1L).nome("Maçã").volume(1.).price(BigDecimal.valueOf(1.)).category(ProductCategory.FRESCO).build());
        productRepository.save(Product.builder().id(2L).nome("Melancia").volume(20.).price(BigDecimal.valueOf(15.30)).category(ProductCategory.FRESCO).build());
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
        batchStockRepository.save(BatchStock.builder().id(1L).product(product1).currentTemperature(20F).minimumTemperature(10F).initialQuantity(20).currentQuantity(20).manufacturingDateTime(LocalDateTime.now()).dueDate(LocalDate.parse("2023-01-01")).inboundOrder(inboundOrder).build());
        batchStockRepository.save(BatchStock.builder().id(2L).product(product2).currentTemperature(20F).minimumTemperature(10F).initialQuantity(20).currentQuantity(20).manufacturingDateTime(LocalDateTime.now()).dueDate(LocalDate.parse("2022-05-01")).inboundOrder(inboundOrder).build());
    }
}
