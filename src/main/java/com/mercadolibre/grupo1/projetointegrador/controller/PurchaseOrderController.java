package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProduct() {
        List<ProductDTO> allProducts = productService.listAllProducts();
        return ResponseEntity.ok().body(allProducts);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listProductForCategory(@RequestParam(required = false, name = "status") ProductCategory productCategory) throws Exception {
        List<ProductDTO> productByCategory = productService.listProductByCategory(productCategory);
        return ResponseEntity.ok().body(productByCategory);
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
