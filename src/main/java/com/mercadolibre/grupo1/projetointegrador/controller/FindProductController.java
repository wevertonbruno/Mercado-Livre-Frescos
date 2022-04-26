package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.FindProductResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.services.FindProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/list")
public class FindProductController {

    private final FindProductService findProductService;
    public FindProductController(FindProductService findProductService) {
        this.findProductService = findProductService;
    };

    @GetMapping("/{productId}")
    public ResponseEntity<FindProductResponseDTO> findProduct(@PathVariable Long productId, @RequestParam Character sortingType) {
        return ResponseEntity.ok().body(new FindProductResponseDTO());
    }
}
