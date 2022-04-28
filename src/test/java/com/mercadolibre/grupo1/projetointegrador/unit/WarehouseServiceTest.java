package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.repositories.WarehouseRepository;
import com.mercadolibre.grupo1.projetointegrador.services.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @Mock
    private WarehouseRepository warehouseRepository;
    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    @DisplayName("Testa se os produtos e suas quantidades são mostrados por armazém")
    public void itShouldReturnTheProductsByWarehouse(){
        List<WarehouseProductDTO> warehouse = createWarehouse();
        Mockito.when(warehouseRepository.findProductsInWarehouse(1L)).thenReturn(warehouse);
        List<WarehouseProductDTO> warehouse1 = warehouseService.findWarehouse(1L);
        Assertions.assertEquals(warehouse1, warehouse);
    }
    public List<WarehouseProductDTO> createWarehouse(){
        WarehouseProductDTO createWarehouse = new WarehouseProductDTO();
        createWarehouse.setWarehouseCode(1L);
        createWarehouse.setTotalQuantity(34L);
        WarehouseProductDTO createWarehouse1 = new WarehouseProductDTO();
        createWarehouse.setWarehouseCode(2L);
        createWarehouse.setTotalQuantity(44L);
        WarehouseProductDTO createWarehouse2 = new WarehouseProductDTO();
        createWarehouse.setWarehouseCode(3L);
        createWarehouse.setTotalQuantity(55L);

        return Arrays.asList(createWarehouse);
    }

}
