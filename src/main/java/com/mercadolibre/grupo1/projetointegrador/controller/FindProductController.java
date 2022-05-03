package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.FindProductResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.SortingType;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import com.mercadolibre.grupo1.projetointegrador.services.FindProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rogério Lambert
 * Controlador responsável pela rota de busca de lotes de um determinado produto de um determinado representante
 * podem ordenar o retorno por id do lote, quantidade corrente e data de vencimento (requisito 3)
 */

@RestController
@RequestMapping("/api/v1/fresh-products/list")
@RequiredArgsConstructor
public class FindProductController {

    private final AuthService authService;

    private final FindProductsService findProductsService;

    @GetMapping("/{productId}")
    public ResponseEntity<FindProductResponseDTO> findProduct(@PathVariable Long productId, @RequestParam(required = false) Character type) {
        SortingType sortingType = SortingType.BATH_ID;
        if (type != null) {
            switch (type) {
                case 'C':
                    sortingType = SortingType.QUANTITY;
                    break;
                case 'F':
                    sortingType = SortingType.DUE_DATE;
                    break;
            }
        }

        Agent agent = authService.getPrincipalAs(Agent.class);

        FindProductResponseDTO findProductResponseDTO = findProductsService.findProducts(productId, sortingType, agent);
        return ResponseEntity.ok().body(findProductResponseDTO);
    }
}
