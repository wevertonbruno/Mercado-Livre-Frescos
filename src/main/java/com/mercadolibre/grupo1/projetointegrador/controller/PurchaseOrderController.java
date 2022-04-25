package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class PurchaseOrderController {

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProduct() {
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listProductForCategory(@RequestParam(required = false, name = "status") PurchaseOrder orderStatus) {
        return null;
    }

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder,
                                                             UriComponentsBuilder uriBuilder) {
        //...

        URI uri =  uriBuilder
                .path("/{idOrder}")
                .buildAndExpand(purchaseOrder.getIdOrder())
                .toUri();

        //...
        return null;
    }

    @GetMapping("/{idOrder}")
    public ResponseEntity<PurchaseOrder> showProductsOrder(@PathVariable("idOrder") Long idOrder) {

        return null;
    }

    @PutMapping("/orders/{idOrder}")
    public ResponseEntity<PurchaseOrder> modifyOrderStatusByOpenedOrClosed(@PathVariable Long idOrder,
                                                                           @RequestBody PurchaseOrderStatusDTO statusOrder) {

        return null;
    }
}
