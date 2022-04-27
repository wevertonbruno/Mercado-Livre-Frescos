package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.controller.WarehouseController;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final WarehouseRepository warehouseRepository;
    private final SectionRepository sectionRepository;
    private final InboundOrderRepository inboundOrderRepository;
    private final BatchStockRepository batchStockRepository;
    private final ProductRepository productRepository;

    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
        seedAgents();
        seedWarehouses();
        seedSections();
        seedInboundOrders();
        seedBatchStocks();
        seedProducts();

        LOGGER.info("Seeding complete...");
    }

    private void seedRoles() {
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
    }

    private void seedSellers() {
        Role sellerRole = roleRepository.findById(2L).get();
        sellerRepository.save(Seller.builder().id(1L).username("seller1").password("123456").email("seller1" +
                "@mercadolibre.com").roles(Set.of(sellerRole)).build());
        sellerRepository.save(Seller.builder().id(2L).username("seller2").password("123456").email("seller2" +
                "@mercadolibre.com").roles(Set.of(sellerRole)).build());
    }

    private void seedAgents() {
        Role agentRole = roleRepository.findById(1L).get();
        agentRepository.save(Agent.builder().id(1L).username("agent1").password("123456").email("agent1@mercadolibre" +
                ".com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent2").password("123456").email("agent2@mercadolibre" +
                ".com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent3").password("123456").email("agent3@mercadolibre" +
                ".com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent4").password("123456").email("agent4@mercadolibre" +
                ".com").roles(Set.of(agentRole)).build());
    }

    private void seedWarehouses() {
        warehouseRepository.save(Warehouse.builder().id(1L).address("endereço 1").name("SP-001").build());
        warehouseRepository.save(Warehouse.builder().id(2L).address("endereço 2").name("RJ-001").build());
        warehouseRepository.save(Warehouse.builder().id(3L).address("endereço 3").name("RN-003").build());

    }

    private void seedSections() {
        Warehouse warehouseSection1 = warehouseRepository.getById(1L);
        Warehouse warehouseSection2 = warehouseRepository.getById(2L);
        Warehouse warehouseSection3 = warehouseRepository.getById(3L);
        sectionRepository.save(Section.builder().id(1L).description("FS").capacity(500.0).warehouse(warehouseSection1).build());
        sectionRepository.save(Section.builder().id(2L).description("RF").capacity(1345.5).warehouse(warehouseSection2).build());
        sectionRepository.save(Section.builder().id(3L).description("FF").capacity(7645.0).warehouse(warehouseSection3).build());
    }

    private void seedInboundOrders() {

    }

    private void seedBatchStocks() {


    }

    private void seedProducts() {

    }
}
