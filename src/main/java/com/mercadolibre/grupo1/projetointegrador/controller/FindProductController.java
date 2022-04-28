package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.FindProductResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.SortingType;
import com.mercadolibre.grupo1.projetointegrador.services.FindProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/list")
@RequiredArgsConstructor
public class FindProductController {

    private final FindProductsService findProductsService;

    @GetMapping("/{productId}")
    public ResponseEntity<FindProductResponseDTO> findProduct(@PathVariable Long productId, @RequestParam Character type) {
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
        //Enquanto o serviço de autenticação não esta implementado, o id do representado esta sendo hardcoded

        Long agentId = 1L;

        FindProductResponseDTO findProductResponseDTO = findProductsService.findProducts(productId, sortingType, agentId);
        return ResponseEntity.ok().body(new FindProductResponseDTO());
    }
}
