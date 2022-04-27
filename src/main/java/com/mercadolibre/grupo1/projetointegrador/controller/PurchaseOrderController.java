package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProduct() {
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listProductForCategory(@RequestParam(required = false, name = "status") PurchaseOrder orderStatus) {
        return null;
    }

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrderDTO.Response> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrder,
                                                          UriComponentsBuilder uriBuilder) {
        //...
        PurchaseOrder purchaseOrderDTO = purchaseOrderService.createPurchaseOrder(purchaseOrder);

        URI uri =  uriBuilder
                .path("/{idOrder}")
                .buildAndExpand(purchaseOrderDTO.getId())
                .toUri();

        PurchaseOrderDTO.Response response = PurchaseOrderDTO.Response.builder().totalPrice(purchaseOrderDTO.totalPrice()).build();

        //...
        return ResponseEntity.created(uri)
                .body(response);
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
