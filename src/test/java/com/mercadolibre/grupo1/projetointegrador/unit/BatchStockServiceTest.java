package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.services.BatchStockService;
import com.mercadolibre.grupo1.projetointegrador.services.ProductService;
import com.mercadolibre.grupo1.projetointegrador.services.mappers.BatchStockMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchStockServiceTest {
    @Mock
    private ProductService productService;

    @Mock
    private BatchStockRepository batchStockRepository;

    @InjectMocks
    private BatchStockService batchStockService;

    @BeforeEach
    public void setUp(){
        BatchStockMapper batchStockMapper = new BatchStockMapper();
        batchStockService = new BatchStockService(productService, batchStockRepository, batchStockMapper);
    }

    @Test
    public void itShouldCreateABatchStockFromDto(){
        Product product = createFakeProduct();
        BatchStockDTO stockInput = createFakeBatchStockInput();

        when(productService.findById(anyLong())).thenReturn(product);

        BatchStock response = batchStockService.createFromDTO(stockInput);

        assertNotNull(response);
        assertNull(response.getId());
        assertEquals(10, response.getInitialQuantity());
        assertEquals(10, response.getCurrentQuantity());
        assertEquals(5.0F, response.getMinimumTemperature());
        assertEquals(10.0F, response.getCurrentTemperature());
        assertEquals(LocalDate.parse("2022-10-01"), response.getDueDate());
        assertEquals(product, response.getProduct());
    }

    @Test
    public void itShouldUpdateABatchStockFromDto(){
        Product product = createFakeProduct();
        BatchStockDTO stockInput = createFakeBatchStockInput();
        stockInput.setBatchNumber(1L);
        BatchStock foundStock = BatchStock.builder().product(product).currentQuantity(5).build();

        when(productService.findById(anyLong())).thenReturn(product);
        when(batchStockRepository.findById(stockInput.getBatchNumber())).thenReturn(Optional.of(foundStock));

        BatchStock response = batchStockService.updateFromDTO(stockInput);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10, response.getInitialQuantity());
        assertEquals(10, response.getCurrentQuantity());
        assertEquals(5.0F, response.getMinimumTemperature());
        assertEquals(10.0F, response.getCurrentTemperature());
        assertEquals(LocalDate.parse("2022-10-01"), response.getDueDate());
        assertEquals(product, response.getProduct());
    }

    @Test
    public void itShouldReturnAStockSection(){
        when(batchStockRepository.findStockBySectionId(anyLong()))
                .thenReturn(new HashSet<>());

        Set<BatchStock> stock = batchStockService.findBatchStockBySectionId(1L);
        assertNotNull(stock);
    }

    private Product createFakeProduct(){
        return Product.builder().id(1L).name("product").volume(10.0).build();
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

}