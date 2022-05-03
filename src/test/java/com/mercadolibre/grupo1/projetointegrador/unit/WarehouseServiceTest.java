package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.WarehouseRepository;
import com.mercadolibre.grupo1.projetointegrador.services.WarehouseService;
import org.junit.jupiter.api.Assertions;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Nayara Coca
 * descrição de cada teste no displayName
 */

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    @DisplayName("Testa se as quantidades de produtos mostrados no armazém ao pesquisar por ID de produto")
    public void itShouldReturnTheProductsByWarehouse(){
        List<WarehouseProductDTO> warehouse = createWarehouse();
        Mockito.when(warehouseRepository.findProductsInWarehouse(1L)).thenReturn(warehouse);
        List<WarehouseProductDTO> warehouse1 = warehouseService.findWarehouse(1L);
        Assertions.assertEquals(warehouse1, warehouse);
        Assertions.assertEquals(warehouse1.size(),1);
    }
    public List<WarehouseProductDTO> createWarehouse(){
        WarehouseProductDTO createWarehouse = new WarehouseProductDTO();
        createWarehouse.setWarehouseCode(1L);
        createWarehouse.setTotalQuantity(34L);
        WarehouseProductDTO createWarehouse1 = new WarehouseProductDTO();
        createWarehouse.setWarehouseCode(1L);
        createWarehouse.setTotalQuantity(44L);
        WarehouseProductDTO createWarehouse2 = new WarehouseProductDTO();
        createWarehouse.setWarehouseCode(1L);
        createWarehouse.setTotalQuantity(55L);

        return Arrays.asList(createWarehouse);
    }

    @Test
    @DisplayName("Testa se retorna erro ao pesquisar produto que não está localizado")
    public void ItShouldReturnANotFoundException() {
        Mockito.when(warehouseRepository.findProductsInWarehouse(Mockito.anyLong())).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(
                NotFoundException.class,
                        () -> warehouseService.findWarehouse(1L));
        assertEquals(exception.getMessage(), "PRODUTO NÃO ENCONTRADO");

    }

    /**
     * @author Rogério Lambert
     * Testes unitarios do service de gestão da warehouse
     */
    @DisplayName("Testa se a query certa é chamada quando o método findById é chamado retornando um objeto Warehouse: ")
    public void itShouldCallFindById() {
        //setup do test
        Warehouse warehouse = Warehouse.builder().id(1L).build();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        //execução
        Warehouse warehouseReturned = warehouseService.findById(1L);

        //verificação
        assertEquals(warehouse.getId(), warehouseReturned.getId());
    }

    @Test
    @DisplayName("Testa se uma exceção correta é lançada quando o armazém não é encontrado: ")
    public void itShouldThrowNotFoundEntity() {
        //setup do test
        when(warehouseRepository.findById(1L)).thenReturn(Optional.empty());

        //execução
        Exception e = assertThrows(EntityNotFoundException.class, () -> warehouseService.findById(1L));

        //verificação
        assertEquals(e.getMessage(), "A warehouse com ID 1 não está cadastrada");
    }
}
