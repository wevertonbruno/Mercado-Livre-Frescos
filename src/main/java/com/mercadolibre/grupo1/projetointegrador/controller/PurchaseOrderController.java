package com.mercadolibre.grupo1.projetointegrador.controller;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Adicionados os EndPoints para realizacao do crud (exceto delete) da API
 *
 * @author  Jefferson Botelho
 * @since   2022-03-22
 *
 */

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class PurchaseOrderController {

    /**
     *  Foi realizado a uniao dos services ProductService e PurchaseOrderServiceImpl numa so classe as regras de negocio para este controller
     *  sera mantido alguns comentarios para informacao e consulta.
     */

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrderDTO.Response> createPurchaseOrder(@Valid @RequestBody PurchaseOrderDTO purchaseOrder,
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

    // sera retornado uma lista com todos os produtos contidos no carrinho.
    @GetMapping("/orders/{idOrder}")

    public ResponseEntity<PurchaseOrder> showProductsOrder(@PathVariable("idOrder") Long idOrder) {

        return ResponseEntity.ok(purchaseOrderService.showProductsInOrders(idOrder));
    }

    @PutMapping("/orders/{idOrder}")
    public ResponseEntity<PurchaseOrderStatusDTO> modifyOrderStatusByOpenedOrPreparing(@PathVariable Long idOrder,
                                                                           @RequestBody PurchaseOrderStatusDTO statusOrder) {

        PurchaseOrderStatusDTO purchaseOrder = purchaseOrderService.editExistentOrder(idOrder, statusOrder);

        return ResponseEntity.ok(purchaseOrder);
    }

}
