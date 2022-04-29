package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.FindProductResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.SortingType;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ProductNotAvailable;
import com.mercadolibre.grupo1.projetointegrador.repositories.BatchStockRepository;
import com.mercadolibre.grupo1.projetointegrador.services.AgentService;
import com.mercadolibre.grupo1.projetointegrador.services.FindProductsService;
import com.mercadolibre.grupo1.projetointegrador.services.WarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindProductServiceTest {
    @Mock private BatchStockRepository batchStockRepository;
    @Mock private AgentService agentService;
    @Mock private WarehouseService warehouseService;

    @InjectMocks
    private FindProductsService findProductsService;


    /**
     * - teste se as ordenações estão sendo acionadas corretamente
     * - teste se a função validateAgent lança exceção correta
     * - teste se a exceção correta é lançada caso não sejam encontrados lotes para o produto
     */

    private void queryItShouldCalled(SortingType sortingType) {
        //setup do teste
        Warehouse warehouse = Warehouse.builder().id(1L).build();
        Agent agent = Agent.builder().id(3L).warehouse(warehouse).build();
        Product mockProduct = Product.builder().id(1L).build();
        Section mockSection = Section.builder().id(1L).build();
        when(agentService.findById(agent.getId())).thenReturn(agent);
        when(warehouseService.findById(warehouse.getId())).thenReturn(warehouse);
        Set<BatchStock> mockBatchs= new HashSet<>();
        mockBatchs.add(
                BatchStock
                        .builder()
                        .id(1L)
                        .dueDate(LocalDate.of(2022, 12,23))
                        .product(mockProduct)
                        .currentQuantity(300)
                        .inboundOrder(InboundOrder.builder().section(mockSection).build())
                        .build()
        );
        switch (sortingType) {
            case BATH_ID:
                when(batchStockRepository.findStockByProductIdAndWarehouseId(mockProduct.getId(), warehouse.getId()))
                        .thenReturn(mockBatchs);
                break;
            case DUE_DATE:
                when(batchStockRepository.findStockByProductIdAndWarehouseIdOrderByDueDate(mockProduct.getId(), warehouse.getId()))
                        .thenReturn(mockBatchs);
                break;
            case QUANTITY:
                when(batchStockRepository.findStockByProductIdAndWarehouseIdOrderByCurrentQuantity(mockProduct.getId(), warehouse.getId()))
                        .thenReturn(mockBatchs);
                break;
        }

        //execução
        FindProductResponseDTO response = findProductsService.findProducts(mockProduct.getId(), sortingType, agent.getId());

        //Testes
        assertEquals(response.getBatchStock().get(0).getBatchNumber(), 1L);
        assertEquals(response.getBatchStock().get(0).getDueDate(), LocalDate.of(2022, 12,23));
        assertEquals(response.getBatchStock().get(0).getCurrentQuantity(), 300);
        assertEquals(response.getSection().getSectionCode(), 1L);
        assertEquals(response.getSection().getWarehouseCode(), 1L);
        assertEquals(response.getProductId(), "1");
    }

    @Test
    @DisplayName("Testa se a query de ordenação por id é acionada quando o sortingType é BATCH_ID:")
    public void itShouldReturnBatchSortingById() {
        queryItShouldCalled(SortingType.BATH_ID);
    }

    @Test
    @DisplayName("Testa se a query de ordenação por quantidade é acionada quando o sortingType é QUANTITY:")
    public void itShouldReturnBatchSortingByCurrentQuantity() {
        queryItShouldCalled(SortingType.QUANTITY);
    }

    @Test
    @DisplayName("Testa se a query de ordenação por quantidade é acionada quando o sortingType é DUE_DATE:")
    public void itShouldReturnBatchSortingByDueDate() {
        queryItShouldCalled(SortingType.DUE_DATE);
    }

    @Test
    @DisplayName("Testa se exceção correta é lançada quando a busca por lotes retorna vazia:")
    public void itShouldReturnExceptionProductNotFound() {
        //setup do teste
        Warehouse warehouse = Warehouse.builder().id(1L).build();
        Agent agent = Agent.builder().id(3L).warehouse(warehouse).build();
        Product mockProduct = Product.builder().id(1L).build();
        when(agentService.findById(agent.getId())).thenReturn(agent);
        when(warehouseService.findById(warehouse.getId())).thenReturn(warehouse);
        Set<BatchStock> mockBatchs= new HashSet<>();
        when(batchStockRepository.findStockByProductIdAndWarehouseId(mockProduct.getId(), warehouse.getId()))
                .thenReturn(mockBatchs);
        SortingType sortingType = SortingType.BATH_ID;

        //execução
        Exception exception = assertThrows(ProductNotAvailable.class, () -> findProductsService
                .findProducts(mockProduct.getId(), sortingType, agent.getId()));

        //Testes
        assertEquals(exception.getMessage(), "Produto não disponível");
    }

    @Test
    @DisplayName("Testa se exceção correta é lançada quando representante não está cadastrado no armazém:")
    public void itShouldReturnExceptionEntityNotFound() {
        //setup do teste
        Warehouse warehouse = Warehouse.builder().id(1L).build();
        Agent agent = Agent.builder().id(3L).warehouse(warehouse).build();
        Product mockProduct = Product.builder().id(1L).build();
        when(agentService.findById(agent.getId())).thenReturn(agent);
        when(warehouseService.findById(warehouse.getId())).thenThrow(EntityNotFoundException.class);

        SortingType sortingType = SortingType.BATH_ID;

        //execução
        Exception exception = assertThrows(EntityNotFoundException.class, () -> findProductsService
                .findProducts(mockProduct.getId(), sortingType, agent.getId()));

        //Testes
        assertEquals(exception.getMessage(), "O representante com ID 3 não está cadastrado em nenhuma warehouse");
    }
}
