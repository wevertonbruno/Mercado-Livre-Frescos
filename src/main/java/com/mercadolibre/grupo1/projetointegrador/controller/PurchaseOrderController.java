package com.mercadolibre.grupo1.projetointegrador.controller;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Adicionados os EndPoints para realizacao do crud (exceto delete) da API
 *
 * @author  Jefferson Botelho, Gabriel Essenio
 * @since   2022-03-22
 *
 */

@RestController
@RequestMapping("/api/v1/fresh-products")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderServiceIml;

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrderDTO.Response> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrder,
                                                                         UriComponentsBuilder uriBuilder) {
        PurchaseOrder purchaseOrderDTO = purchaseOrderService.createPurchaseOrder(purchaseOrder);

        URI uri = uriBuilder
                .path("/{idOrder}")
                .buildAndExpand(purchaseOrderDTO.getId())
                .toUri();

        PurchaseOrderDTO.Response response = PurchaseOrderDTO.Response.builder().totalPrice(purchaseOrderDTO.totalPrice()).build();
        return ResponseEntity.created(uri).body(response);
    }

    // sera retornado uma lista com todos os produtos contidos no carrinho.
    @GetMapping("/orders/{idOrder}")
    public ResponseEntity<PurchaseOrder> showProductsOrder(@PathVariable("idOrder") Long idOrder) {

        return ResponseEntity.ok(purchaseOrderService.showProductsInOrders(idOrder));
    }

    /**
     * @author Jeffeson ,Gabriel Essenio
     *Controller para atualizar o status da compra quando concluida
     */
    @PutMapping("/orders/{idOrder}/close")
    public ResponseEntity<PurchaseOrder> editStatusExistentOrder(@PathVariable Long idOrder,UriComponentsBuilder uriBuilder) {
        PurchaseOrder purchaseOrder = purchaseOrderService.editExistentOrder(idOrder);
        URI uri = uriBuilder
                .path("/{idOrder}")
                .buildAndExpand(idOrder)
                .toUri();
        return ResponseEntity.created(uri).body(purchaseOrder);

    }
}