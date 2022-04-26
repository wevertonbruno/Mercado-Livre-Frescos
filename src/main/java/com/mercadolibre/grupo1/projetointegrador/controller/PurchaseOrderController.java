package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderStatusDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.services.ProductService;
import com.mercadolibre.grupo1.projetointegrador.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class PurchaseOrderController {


    // Inicialmente o controller esta lidando com dois services, afim de minnimizar conflitos por Gabriel e Jefferson estarem implementando o service ao mesmo tempo.
    // ao fim do requisito 02 a ideia é unir em um unico service.

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

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
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder,
                                                             UriComponentsBuilder uriBuilder) {
        //...

        URI uri =  uriBuilder
                .path("/{idOrder}")
                .buildAndExpand(purchaseOrder.getId())
                .toUri();

        //...
        return null;
    }

    @GetMapping("/{idOrder}")
    public ResponseEntity<PurchaseOrder> showProductsOrder(@PathVariable("idOrder") Long idOrder) {

        return ResponseEntity.ok(purchaseOrderService.showProductsInOrders(idOrder));
    }

    @PutMapping("/orders/{idOrder}")
    public ResponseEntity<PurchaseOrder> modifyOrderStatusByOpenedOrClosed(@PathVariable Long idOrder,
                                                                           @RequestBody PurchaseOrder statusOrder) {

        PurchaseOrder purchaseOrder = purchaseOrderService.editExistentOrder(idOrder, statusOrder);
        return ResponseEntity.ok(purchaseOrder);
    }

}
