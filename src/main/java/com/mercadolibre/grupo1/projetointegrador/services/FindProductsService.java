package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.FindProductResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.SortingType;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FindProductsService {
    private final BatchStockRepository batchStockRepository;
    private final AgentService agentService;
    private final WarehouseService warehouseService;

//    public boolean validateFindProduct()
    public void validateAgent(Agent agent) {
        String errorMessage = "O representante com ID " + agent.getId() + " não está cadastrado em nenhuma warehouse";
        try {
            Warehouse warehouse = warehouseService.findById(agent.getWarehouse().getId());
        }
        catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(errorMessage);
        }
    }

    public FindProductResponseDTO findProducts(Long productId, SortingType sortingType, Long agentId) {
        /**
         * verificar se o agente esta persistido
         * verificar se ele esta associado a alguma warehouse
         * buscar lotes do produto filtrados pela warehouse do agente
         * aplicar ordenação se esta for presente
         * gerar o DTO
         */
        Agent agent = agentService.findById(agentId);

        Long warehouseId = agent.getWarehouse().getId();

        validateAgent(agent);
        Set<BatchStock> batchStocks = new HashSet<>();
        switch (sortingType) {
            case BATH_ID:
                batchStocks = batchStockRepository.findStockByProductIdAndWarehouseId(productId, warehouseId);
                break;
            case DUE_DATE:
                batchStocks = batchStockRepository.findStockByProductIdAndWarehouseIdOrderByDueDate(productId, warehouseId);
                break;
            case QUANTITY:
                batchStocks = batchStockRepository.findStockByProductIdAndWarehouseIdOrderByCurrentQuantity(productId, warehouseId);
                break;
        }

        if (batchStocks.isEmpty()) {
            throw new EntityNotFoundException("Produto não disponível");
        }
        Long sectionId = batchStocks.iterator().next().getInboundOrder().getSection().getId();

        return new FindProductResponseDTO(productId, batchStocks, sectionId, warehouseId);
    }
}
