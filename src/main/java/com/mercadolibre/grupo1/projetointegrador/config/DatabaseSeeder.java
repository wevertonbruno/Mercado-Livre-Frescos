package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.entities.*;
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
    private final WarehouseRepository warehouseRepository;
    private final SectionRepository sectionRepository;
    private final ProductRepository productRepository;
    private final BatchStockRepository batchStockRepository;

    @Transactional
    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
        seedAgents();
        seedWarehouses();
        seedBatches();

        LOGGER.info("Seeding complete...");
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

    private void seedWarehouses(){
        Warehouse w1 = warehouseRepository.save(Warehouse.builder().id(1L).name("SP-SP").address("00000-000").build());
        sectionRepository.save(Section.builder().id(1L).capacity(100.0).category(ProductCategory.FRESCO).warehouse(w1).description("Sessao de frescos").build());
        sectionRepository.save(Section.builder().id(2L).capacity(100.0).category(ProductCategory.CONGELADO).warehouse(w1).description("Sessao de congelados").build());
        sectionRepository.save(Section.builder().id(3L).capacity(100.0).category(ProductCategory.REFRIGERADO).warehouse(w1).description("Sessao de refrigerados").build());
    }

    private void seedBatches(){
        Product p1 = productRepository.save(Product.builder().id(1L).category(ProductCategory.CONGELADO).price(BigDecimal.TEN).nome("peixe").volume(10.0).build());
        Product p2 = productRepository.save(Product.builder().id(2L).category(ProductCategory.FRESCO).price(BigDecimal.TEN).nome("sardinha").volume(5.0).build());
        Product p3 = productRepository.save(Product.builder().id(3L).category(ProductCategory.REFRIGERADO).price(BigDecimal.TEN).nome("carne").volume(15.0).build());

        batchStockRepository.save(
                BatchStock.builder().id(1L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,06, 25))
                        .build());

        batchStockRepository.save(
                BatchStock.builder().id(2L)
                        .product(p2)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,06, 25))
                        .build());

        batchStockRepository.save(
                BatchStock.builder().id(3L)
                        .product(p3)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,06, 25))
                        .build());
    }
}
