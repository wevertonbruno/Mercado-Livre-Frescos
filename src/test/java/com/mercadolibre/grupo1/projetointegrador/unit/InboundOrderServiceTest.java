package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.OvercapacityException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidOperationException;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.InboundOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SectionRepository;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import com.mercadolibre.grupo1.projetointegrador.services.BatchStockService;
import com.mercadolibre.grupo1.projetointegrador.services.InboundOrderService;
import com.mercadolibre.grupo1.projetointegrador.services.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitarios da classe InboundOrderService
 * @author Weverton Bruno
 */
@ExtendWith(MockitoExtension.class)
class InboundOrderServiceTest {
    @Mock private InboundOrderRepository inboundOrderRepository;
    @Mock private SectionRepository sectionRepository;
    @Mock private ProductRepository productRepository;
    @Mock private BatchStockRepository batchStockRepository;
    @Mock private SectionService sectionService;
    @Mock private BatchStockService batchStockService;
    @Mock private AuthService authService;

    @InjectMocks
    private InboundOrderService inboundOrderService;

    @Test
    @DisplayName("Testa se uma exceção de categoria invalida é lancada ao tentar cadastrar um lote numa sessao de categorias diferentes")
    public void itShouldReturnAInvalidCategoryException(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
        Agent agent = createFakeAgent(section.getWarehouse());

        section.setCategory(ProductCategory.FRESCO);
        Product product1 = Product.builder().category(ProductCategory.FRESCO).build();
        Product product2 = Product.builder().category(ProductCategory.CONGELADO).build();

        BatchStockDTO batch1 = createFakeBatchStockInput();
        BatchStockDTO batch2 = createFakeBatchStockInput();
        inboundOrderDTO.setBatchStock(Arrays.asList(batch1, batch2));

        when(sectionService.findBySectionDto(inboundOrderDTO.getSection()))
                .thenReturn(section);
        when(batchStockService.createFromDTO(batch1))
                .thenReturn(BatchStock.builder().product(product1).build());
        when(batchStockService.createFromDTO(batch2))
                .thenReturn(BatchStock.builder().product(product2).build());
        when(authService.getPrincipalAs(Agent.class)).thenReturn(agent);

        Exception exception = assertThrows(InvalidCategoryException.class, () -> inboundOrderService.createInboundOrder(inboundOrderDTO));

        assertEquals(exception.getMessage(), "Só é permitido produtos da categoria FRESCO nesta sessão.");
    }

    @Test
    @DisplayName("Testa se uma exceção é lancada quando o volume dos lotes excede a capacidade da sessão")
    public void itShouldReturnAOvercapacityException(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
        Agent agent = createFakeAgent(section.getWarehouse());

        section.setCapacity(100.0);
        section.setCategory(ProductCategory.FRESCO);

        Product product1 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();
        Product product2 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();

        BatchStock createdBatch1 = BatchStock.builder().product(product1).currentQuantity(10).build();
        BatchStock createdBatch2 = BatchStock.builder().product(product2).currentQuantity(5).build();

        inboundOrderDTO.setBatchStock(Arrays.asList(
                createFakeBatchStockInput(),
                createFakeBatchStockInput()
        ));

        when(sectionService.findBySectionDto(inboundOrderDTO.getSection()))
                .thenReturn(section);
        when(batchStockService.createFromDTO(any(BatchStockDTO.class)))
                .thenReturn(createdBatch1, createdBatch2);
        when(batchStockService.findBatchStockBySectionId(anyLong()))
                .thenReturn(new HashSet<>());
        when(authService.getPrincipalAs(Agent.class)).thenReturn(agent);

        Exception exception = assertThrows(OvercapacityException.class, () -> inboundOrderService.createInboundOrder(inboundOrderDTO));

        assertEquals(exception.getMessage(), "Capacidade da sessao excedida! Capacidade total da sessao: 100,00m3, Capacidade ocupada: 0,00m3, Após a operacao: 150,00m3.");
    }

    @Test
    @DisplayName("Testa se uma ordem de entrada é registrada")
    public void itShouldRegisterAInboundOrder(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
        Agent agent = createFakeAgent(section.getWarehouse());
        section.setCapacity(100.0);
        section.setCategory(ProductCategory.FRESCO);

        Product product1 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();
        Product product2 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();

        BatchStock createdBatch1 = BatchStock.builder().product(product1).currentQuantity(5).build();
        BatchStock createdBatch2 = BatchStock.builder().product(product2).currentQuantity(5).build();

        inboundOrderDTO.setBatchStock(Arrays.asList(
                createFakeBatchStockInput(),
                createFakeBatchStockInput()
        ));

        when(sectionService.findBySectionDto(inboundOrderDTO.getSection()))
                .thenReturn(section);
        when(batchStockService.createFromDTO(any(BatchStockDTO.class)))
                .thenReturn(createdBatch1, createdBatch2);
        when(batchStockService.findBatchStockBySectionId(anyLong()))
                .thenReturn(new HashSet<>());
        when(authService.getPrincipalAs(Agent.class)).thenReturn(agent);

        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(
                InboundOrder.builder()
                        .section(section)
                        .batchStock(Arrays.asList(createdBatch1, createdBatch2))
                        .orderDate(inboundOrderDTO.getOrderDate())
                        .id(1L)
                        .build()
        );

        InboundOrderResponseDTO inboundOrderResponse = inboundOrderService.createInboundOrder(inboundOrderDTO);

        assertNotNull(inboundOrderResponse);
        assertEquals(1, inboundOrderResponse.getOrderNumber());
        assertEquals(2, inboundOrderResponse.getBatchStocks().size());
    }

    @Test
    @DisplayName("Testa se uma ordem de entrada é atualizada")
    public void itShouldUpdateAInboundOrder(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
        Agent agent = createFakeAgent(section.getWarehouse());
        section.setCapacity(100.0);
        section.setCategory(ProductCategory.FRESCO);

        Product product1 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();
        Product product2 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();

        BatchStock createdBatch1 = BatchStock.builder().product(product1).currentQuantity(5).build();
        BatchStock createdBatch2 = BatchStock.builder().id(1L).product(product2).currentQuantity(5).build();

        BatchStockDTO batchToCreate = createFakeBatchStockInput();
        BatchStockDTO batchToUpdate = createFakeBatchStockInput();
        batchToUpdate.setBatchNumber(1L);

        inboundOrderDTO.setBatchStock(Arrays.asList(
                batchToCreate,
                batchToUpdate
        ));

        InboundOrder inboundFound = InboundOrder.builder()
                .section(section)
                .batchStock(new ArrayList<>(Arrays.asList(createdBatch1, createdBatch2)))
                .orderDate(inboundOrderDTO.getOrderDate())
                .id(1L)
                .build();

        createdBatch2.setInboundOrder(inboundFound);

        when(inboundOrderRepository.findById(anyLong()))
                .thenReturn(Optional.of(inboundFound));

        when(batchStockService.createFromDTO(batchToCreate))
                .thenReturn(createdBatch1);

        when(batchStockService.updateFromDTO(batchToUpdate))
                .thenReturn(createdBatch2);

        when(batchStockService.findBatchStockBySectionId(anyLong()))
                .thenReturn(new HashSet<>());

        when(authService.getPrincipalAs(Agent.class)).thenReturn(agent);

        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(inboundFound);

        InboundOrderResponseDTO inboundOrderResponse = inboundOrderService.updateOrder(1L, inboundOrderDTO);

        assertNotNull(inboundOrderResponse);
        assertEquals(1, inboundOrderResponse.getOrderNumber());
        assertEquals(2, inboundOrderResponse.getBatchStocks().size());
    }

    @Test
    @DisplayName("Testa se uma exceção de Operacao Invalida é lancada ao tentar atualizar lotes de outra Ordem")
    public void itShouldThrowAInvalidOperationException(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
        Agent agent = createFakeAgent(section.getWarehouse());
        section.setCapacity(100.0);
        section.setCategory(ProductCategory.FRESCO);

        Product product1 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();
        Product product2 = Product.builder().volume(10.0).category(ProductCategory.FRESCO).build();

        BatchStock createdBatch1 = BatchStock.builder().product(product1).currentQuantity(5).build();
        BatchStock createdBatch2 = BatchStock.builder().id(1L).product(product2).currentQuantity(5).build();

        BatchStockDTO batchToCreate = createFakeBatchStockInput();
        BatchStockDTO batchToUpdate = createFakeBatchStockInput();
        batchToUpdate.setBatchNumber(1L);

        inboundOrderDTO.setBatchStock(Arrays.asList(
                batchToCreate,
                batchToUpdate
        ));

        InboundOrder inboundFound = InboundOrder.builder()
                .section(section)
                .batchStock(new ArrayList<>(Arrays.asList(createdBatch1, createdBatch2)))
                .orderDate(inboundOrderDTO.getOrderDate())
                .id(1L)
                .build();

        InboundOrder anotherOrder = InboundOrder.builder()
                .section(section)
                .batchStock(new ArrayList<>(Arrays.asList(createdBatch1, createdBatch2)))
                .orderDate(inboundOrderDTO.getOrderDate())
                .id(2L)
                .build();

        createdBatch2.setInboundOrder(anotherOrder);

        when(inboundOrderRepository.findById(anyLong()))
                .thenReturn(Optional.of(inboundFound));

        when(batchStockService.createFromDTO(batchToCreate))
                .thenReturn(createdBatch1);

        when(batchStockService.updateFromDTO(batchToUpdate))
                .thenReturn(createdBatch2);

        Exception exception = assertThrows(InvalidOperationException.class, () -> {
            inboundOrderService.updateOrder(1L, inboundOrderDTO);
        });

        assertEquals("O lote de ID 1 não pertence a esta Ordem de Entrada!", exception.getMessage());
    }

    @Test
    @DisplayName("Testa se uma exceção de Ordem nao encontrada é lancada")
    public void itShouldThrowAOrderNotFoundException(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();

        when(inboundOrderRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> inboundOrderService.updateOrder(1L, inboundOrderDTO));
        assertEquals(exception.getMessage(), "Ordem com ID 1 não encontrada");
    }

    private InboundOrderDTO createFakeOrderInput(){
        InboundOrderDTO inboundOrderDTO = new InboundOrderDTO();
        SectionDTO sectionDTO = new SectionDTO();

        sectionDTO.setSectionCode(1L);
        sectionDTO.setWarehouseCode(1L);

        inboundOrderDTO.setOrderDate(LocalDate.parse("2022-01-01"));
        inboundOrderDTO.setSection(sectionDTO);
        inboundOrderDTO.setBatchStock(new ArrayList<>());

        return inboundOrderDTO;
    }

    private BatchStockDTO createFakeBatchStockInput(){
        BatchStockDTO item = new BatchStockDTO();
        item.setBatchNumber(null);
        item.setInitialQuantity(10);
        item.setCurrentQuantity(10);
        item.setCurrentTemperature(10.0F);
        item.setMinimumTemperature(5.0F);
        item.setDueDate(LocalDate.parse("2022-10-01"));
        item.setManufacturingDateTime(LocalDateTime.parse("2022-01-01T00:00:00"));
        item.setProductId(1L);

        return item;
    }

    private Section createFakeSection(){
        Section section = Section.builder()
                .id(1L)
                .capacity(50.0)
                .category(ProductCategory.FRESCO)
                .warehouse(Warehouse.builder().id(1L).address("555").name("SP").build())
                        .build();
        return section;
    }

    private Agent createFakeAgent(Warehouse warehouse){
        Agent agent = new Agent();
        agent.setId(1L);
        agent.setWarehouse(warehouse);
        return agent;
    }
}