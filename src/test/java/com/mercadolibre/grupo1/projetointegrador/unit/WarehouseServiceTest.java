package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.WarehouseRepository;
import com.mercadolibre.grupo1.projetointegrador.services.WarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * @author Rogério Lambert
 * Testes unitarios do service de gestão da warehouse
 */

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
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
