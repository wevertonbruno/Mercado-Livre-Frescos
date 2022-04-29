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

import java.util.ArrayList;

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
    private final WarehouseRepository warehouseRepository;
    private final SectionRepository sectionRepository;
    private final ProductRepository productRepository;
    private final BatchStockRepository batchStockRepository;
    private final InboundOrderRepository inboundOrderRepository;

    @Transactional
    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
//        seedAgents();
        seedWarehouses();
        seedProducts();

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

//    private void seedAgents(){
//        Role agentRole = roleRepository.findById(1L).get();
//        agentRepository.save(Agent.builder().id(1L).username("agent1").password("123456").email("agent1@mercadolibre.com").roles(Set.of(agentRole)).build());
//        agentRepository.save(Agent.builder().id(2L).username("agent2").password("123456").email("agent2@mercadolibre.com").roles(Set.of(agentRole)).build());
//        agentRepository.save(Agent.builder().id(3L).username("agent3").password("123456").email("agent3@mercadolibre.com").roles(Set.of(agentRole)).build());
//        agentRepository.save(Agent.builder().id(4L).username("agent4").password("123456").email("agent4@mercadolibre.com").roles(Set.of(agentRole)).build());
//    }

    private void seedWarehouses(){
        Warehouse w1 = warehouseRepository.save(Warehouse.builder().id(1L).name("SP-SP").address("00000-000").build());
        sectionRepository.save(Section.builder().id(1L).capacity(100.0).category(ProductCategory.FRESCO).warehouse(w1).description("Sessao de frescos").build());
        sectionRepository.save(Section.builder().id(2L).capacity(100.0).category(ProductCategory.CONGELADO).warehouse(w1).description("Sessao de congelados").build());
        sectionRepository.save(Section.builder().id(3L).capacity(100.0).category(ProductCategory.REFRIGERADO).warehouse(w1).description("Sessao de refrigerados").build());

        Warehouse w2 = warehouseRepository.save(Warehouse.builder().id(2L).name("BH-MG").address("00000-000").build());
        sectionRepository.save(Section.builder().id(4L).capacity(100.0).category(ProductCategory.FRESCO).warehouse(w2).description("Sessao de frescos").build());
        sectionRepository.save(Section.builder().id(5L).capacity(100.0).category(ProductCategory.CONGELADO).warehouse(w2).description("Sessao de congelados").build());
        sectionRepository.save(Section.builder().id(6L).capacity(100.0).category(ProductCategory.REFRIGERADO).warehouse(w2).description("Sessao de refrigerados").build());

        Role agentRole = roleRepository.findById(1L).get();

        agentRepository.save(Agent.builder().id(3L).username("agent1").password("123456").email("agent1@mercadolibre.com").roles(Set.of(agentRole)).warehouse(w1).build());
        agentRepository.save(Agent.builder().id(4L).username("agent2").password("123456").email("agent2@mercadolibre.com").roles(Set.of(agentRole)).warehouse(w1).build());
        agentRepository.save(Agent.builder().id(5L).username("agent3").password("123456").email("agent3@mercadolibre.com").roles(Set.of(agentRole)).warehouse(w2).build());
        agentRepository.save(Agent.builder().id(6L).username("agent4").password("123456").email("agent4@mercadolibre.com").roles(Set.of(agentRole)).warehouse(w2).build());

    }

    private void seedProducts() {
        Seller s1 = sellerRepository.findById(1L).get();
        Seller s2 = sellerRepository.findById(2L).get();

        Product p1 = productRepository.save(Product.builder().id(1L).seller(s1).category(ProductCategory.CONGELADO).price(BigDecimal.TEN).nome("peixe").volume(10.0).build());
        Product p2 = productRepository.save(Product.builder().id(2L).seller(s2).category(ProductCategory.FRESCO).price(BigDecimal.TEN).nome("sardinha").volume(5.0).build());
        Product p3 = productRepository.save(Product.builder().id(3L).seller(s1).category(ProductCategory.REFRIGERADO).price(BigDecimal.TEN).nome("carne").volume(15.0).build());

        Section fresh1 = sectionRepository.findById(1L).get();
        InboundOrder inboundOrderFresh1 = inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(fresh1).id(1L).batchStock(null).build());
        Section frozen1 = sectionRepository.findById(2L).get();
        InboundOrder inboundOrderFrozen1 = inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(frozen1).id(2L).batchStock(null).build());
        Section refrigerated1 = sectionRepository.findById(3L).get();
        InboundOrder inboundOrderRefrigerated1 = inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(refrigerated1).id(3L).batchStock(null).build());

        Section fresh2 = sectionRepository.findById(4L).get();
        InboundOrder inboundOrderFresh2 = inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(fresh2).id(4L).batchStock(null).build());
        Section frozen2 = sectionRepository.findById(5L).get();
        InboundOrder inboundOrderFrozen2 = inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(frozen2).id(5L).batchStock(null).build());
        Section refrigerated2 = sectionRepository.findById(6L).get();
        InboundOrder inboundOrderRefrigerated2 = inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(refrigerated2).id(6L).batchStock(null).build());

        List<BatchStock> batchStocksFrozen1 = new ArrayList<>();
        batchStocksFrozen1.add(batchStockRepository.save(
                BatchStock.builder().id(1L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(8)
                        .currentTemperature(-15.0F)
                        .minimumTemperature(-45.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 29))
                        .inboundOrder(inboundOrderFrozen1)
                        .build()));

        batchStocksFrozen1.add(batchStockRepository.save(
                BatchStock.builder().id(2L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(-15.0F)
                        .minimumTemperature(-45.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 30))
                        .inboundOrder(inboundOrderFrozen1)
                        .build()));

        batchStocksFrozen1.add(batchStockRepository.save(
                BatchStock.builder().id(3L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(4)
                        .currentTemperature(-15.0F)
                        .minimumTemperature(-45.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 28))
                        .inboundOrder(inboundOrderFrozen1)
                        .build()));

        inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(frozen1).id(1L).batchStock(batchStocksFrozen1).build());

        List<BatchStock> batchStocksFrozen2 = new ArrayList<>();
        batchStocksFrozen2.add(batchStockRepository.save(
                BatchStock.builder().id(4L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(-15.0F)
                        .minimumTemperature(-45.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,10, 25))
                        .inboundOrder(inboundOrderFrozen2)
                        .build()));

        batchStocksFrozen2.add(batchStockRepository.save(
                BatchStock.builder().id(5L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(8)
                        .currentTemperature(-15.0F)
                        .minimumTemperature(-45.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,8, 25))
                        .inboundOrder(inboundOrderFrozen2)
                        .build()));

        batchStocksFrozen2.add(batchStockRepository.save(
                BatchStock.builder().id(6L)
                        .product(p1)
                        .initialQuantity(10)
                        .currentQuantity(6)
                        .currentTemperature(-15.0F)
                        .minimumTemperature(-45.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,7, 25))
                        .inboundOrder(inboundOrderFrozen2)
                        .build()));

        inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(frozen2).id(5L).batchStock(batchStocksFrozen2).build());

        List<BatchStock> batchStocksFresh1 = new ArrayList<>();

        batchStocksFresh1.add(batchStockRepository.save(
                BatchStock.builder().id(7L)
                        .product(p2)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 25))
                        .inboundOrder(inboundOrderFresh1)
                        .build()));

        batchStocksFresh1.add(batchStockRepository.save(
                BatchStock.builder().id(8L)
                        .product(p2)
                        .initialQuantity(10)
                        .currentQuantity(8)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 30))
                        .inboundOrder(inboundOrderFresh1)
                        .build()));

        batchStocksFresh1.add(batchStockRepository.save(
                BatchStock.builder().id(9L)
                        .product(p2)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,5, 6))
                        .inboundOrder(inboundOrderFresh1)
                        .build()));

        inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(fresh1).id(1L).batchStock(batchStocksFresh1).build());

        List<BatchStock> batchStocksFresh2 = new ArrayList<>();

        batchStocksFresh2.add(batchStockRepository.save(
                BatchStock.builder().id(10L)
                        .product(p2)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 25))
                        .inboundOrder(inboundOrderFresh2)
                        .build()));

        batchStocksFresh2.add(batchStockRepository.save(
                BatchStock.builder().id(11L)
                        .product(p2)
                        .initialQuantity(30)
                        .currentQuantity(26)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,5, 6))
                        .inboundOrder(inboundOrderFresh2)
                        .build()));

        batchStocksFresh2.add(batchStockRepository.save(
                BatchStock.builder().id(12L)
                        .product(p2)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(15.0F)
                        .minimumTemperature(10.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,5, 6))
                        .inboundOrder(inboundOrderFresh2)
                        .build()));

        inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(fresh2).id(4L).batchStock(batchStocksFresh2).build());

        List<BatchStock> batchStocksRefrigerated1 = new ArrayList<>();

        batchStocksRefrigerated1.add(batchStockRepository.save(
                BatchStock.builder().id(13L)
                        .product(p3)
                        .initialQuantity(20)
                        .currentQuantity(18)
                        .currentTemperature(8.0F)
                        .minimumTemperature(1.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2023,6, 25))
                        .inboundOrder(inboundOrderRefrigerated1)
                        .build()));

        batchStocksRefrigerated1.add(batchStockRepository.save(
                BatchStock.builder().id(14L)
                        .product(p3)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(8.0F)
                        .minimumTemperature(1.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 25))
                        .inboundOrder(inboundOrderRefrigerated1)
                        .build()));

        batchStocksRefrigerated1.add(batchStockRepository.save(
                BatchStock.builder().id(15L)
                        .product(p3)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(8.0F)
                        .minimumTemperature(1.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,8, 25))
                        .inboundOrder(inboundOrderRefrigerated1)
                        .build()));

        inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(refrigerated1).id(3L).batchStock(batchStocksRefrigerated1).build());

        List<BatchStock> batchStocksRefrigerated2 = new ArrayList<>();

        batchStocksRefrigerated2.add(batchStockRepository.save(
                BatchStock.builder().id(16L)
                        .product(p3)
                        .initialQuantity(10)
                        .currentQuantity(10)
                        .currentTemperature(8.0F)
                        .minimumTemperature(1.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 25))
                        .inboundOrder(inboundOrderRefrigerated2)
                        .build()));

        batchStocksRefrigerated2.add(batchStockRepository.save(
                BatchStock.builder().id(17L)
                        .product(p3)
                        .initialQuantity(10)
                        .currentQuantity(8)
                        .currentTemperature(8.0F)
                        .minimumTemperature(1.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,7, 25))
                        .inboundOrder(inboundOrderRefrigerated2)
                        .build()));

        batchStocksRefrigerated2.add(batchStockRepository.save(
                BatchStock.builder().id(18L)
                        .product(p3)
                        .initialQuantity(10)
                        .currentQuantity(4)
                        .currentTemperature(8.0F)
                        .minimumTemperature(1.0F)
                        .manufacturingDateTime(LocalDateTime.now())
                        .dueDate(LocalDate.of(2022,6, 10))
                        .inboundOrder(inboundOrderRefrigerated2)
                        .build()));

        inboundOrderRepository.save(InboundOrder.builder().orderDate(LocalDate.now()).section(refrigerated2).id(6L).batchStock(batchStocksRefrigerated2).build());


    }
}
