package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExcededCapacityException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidCategoryException;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.InboundOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SectionRepository;
import com.mercadolibre.grupo1.projetointegrador.services.BatchStockService;
import com.mercadolibre.grupo1.projetointegrador.services.InboundOrderService;
import com.mercadolibre.grupo1.projetointegrador.services.SectionService;
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

@ExtendWith(MockitoExtension.class)
class InboundOrderServiceTest {
    @Mock private InboundOrderRepository inboundOrderRepository;
    @Mock private SectionRepository sectionRepository;
    @Mock private ProductRepository productRepository;
    @Mock private BatchStockRepository batchStockRepository;
    @Mock private SectionService sectionService;
    @Mock private BatchStockService batchStockService;

    @InjectMocks
    private InboundOrderService inboundOrderService;

    @Test
    public void itShouldReturnAInvalidCategoryException(){

        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();

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

        Exception exception = assertThrows(InvalidCategoryException.class, () -> inboundOrderService.createInboundOrder(inboundOrderDTO));

        assertEquals(exception.getMessage(), "Só é permitido produtos da categoria FRESCO nesta sessão.");
    }

    @Test
    public void itShouldReturnAExcededCapacityException(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
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

        Exception exception = assertThrows(ExcededCapacityException.class, () -> inboundOrderService.createInboundOrder(inboundOrderDTO));

        assertEquals(exception.getMessage(), "Capacidade da sessao excedida! Capacidade total da sessao: 100,00m3, Capacidade ocupada: 0,00m3, Após a operacao: 150,00m3.");
    }

    @Test
    public void itShouldRegisterAInboundOrder(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
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

        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(
                InboundOrder.builder()
                        .section(section)
                        .batchStock(new ArrayList<>())
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
    public void itShouldUpdateAInboundOrder(){
        InboundOrderDTO inboundOrderDTO = createFakeOrderInput();
        Section section = createFakeSection();
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

        when(inboundOrderRepository.findById(anyLong()))
                .thenReturn(Optional.of(inboundFound));

        when(batchStockService.createFromDTO(batchToCreate))
                .thenReturn(createdBatch1);

        when(batchStockService.updateFromDTO(batchToUpdate))
                .thenReturn(createdBatch2);

        when(batchStockService.findBatchStockBySectionId(anyLong()))
                .thenReturn(new HashSet<>());

        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(inboundFound);

        InboundOrderResponseDTO inboundOrderResponse = inboundOrderService.updateOrder(1L, inboundOrderDTO);

        assertNotNull(inboundOrderResponse);
        assertEquals(1, inboundOrderResponse.getOrderNumber());
        assertEquals(2, inboundOrderResponse.getBatchStocks().size());
    }

    @Test
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
}