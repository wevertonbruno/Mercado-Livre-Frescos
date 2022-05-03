package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import com.mercadolibre.grupo1.projetointegrador.services.BatchStockService;
import com.mercadolibre.grupo1.projetointegrador.services.ProductService;
import com.mercadolibre.grupo1.projetointegrador.services.mappers.BatchStockMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitarios da classe BatchStockService
 * @author Weverton Bruno
 */
@ExtendWith(MockitoExtension.class)
class BatchStockServiceTest {
    @Mock
    private ProductService productService;

    @Mock
    private BatchStockRepository batchStockRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private BatchStockService batchStockService;

    @BeforeEach
    public void setUp(){
        BatchStockMapper batchStockMapper = new BatchStockMapper();
        batchStockService = new BatchStockService(productService, batchStockRepository, batchStockMapper, authService);
    }

    @Test
    @DisplayName("Testa se uma classe BatchStock é instanciado a partir de um DTO")
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
    @DisplayName("Testa se uma entidade BatchStock é atualizada a partir de um DTO")
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
    @DisplayName("Testa os lotes da sessao são retornados")
    public void itShouldReturnAStockSection(){
        when(batchStockRepository.findStockBySectionId(anyLong()))
                .thenReturn(new HashSet<>());

        Set<BatchStock> stock = batchStockService.findBatchStockBySectionId(1L);
        assertNotNull(stock);
    }

    @Test
    @DisplayName("Testa se os lotes da sessao são retornados e filtrados por um periodo de vencimento.")
    public void itShouldReturnAStockBySectionIdAndExpiresIn(){
        Product product = createFakeProduct();

        Agent agent = createFakeAgent();
        when(authService.getPrincipalAs(Agent.class)).thenReturn(agent);

        when(batchStockRepository.findStockBySectionIdAndDueDateBetween(1L, LocalDate.now(), LocalDate.now().plusDays(10)))
                .thenReturn(new ArrayList<>(
                        Arrays.asList(BatchStock.builder().product(product).currentQuantity(5).build())
                ));

        List<BatchStockDTO.SimpleBatchStockDTO> stock = batchStockService.findBatchStockBySectionIdAndExpiresIn(1L, 10L);
        assertNotNull(stock);
    }

    @Test
    @DisplayName("Testa se os lotes do armazem são retornados e filtrados por um periodo de vencimento e categoria de produtos.")
    public void itShouldReturnAStockByCategoryAndExpiresIn(){
        Product product = createFakeProduct();

        Agent agent = createFakeAgent();
        when(authService.getPrincipalAs(Agent.class)).thenReturn(agent);

        when(batchStockRepository.findWarehouseStockByCategoryAndDueDateBetween(1L, ProductCategory.FRESCO, LocalDate.now(), LocalDate.now().plusDays(10), Sort.by(Sort.Direction.ASC, "dueDate")))
                .thenReturn(new ArrayList<>(
                        Arrays.asList(BatchStock.builder().product(product).currentQuantity(5).build())
                ));

        List<BatchStockDTO.SimpleBatchStockDTO> stock = batchStockService.findBatchStockByCategoryAndExpiresIn(ProductCategory.FRESCO, 10L, Sort.Direction.ASC);
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

    private Agent createFakeAgent(){
        Agent agent = new Agent();
        Warehouse warehouse =
                Warehouse.builder()
                        .id(1L)
                        .address("1111-111")
                        .name("SP")
                        .agents(Set.of(agent))
                        .sections(Set.of(
                                Section.builder().id(1L).build(),
                                Section.builder().id(2L).build()
                        )).build();
        agent.setId(1L);
        agent.setWarehouse(warehouse);
        return agent;
    }

}