package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.WarehouseProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.PurchaseOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author Nayara Coca
 * criação do controller de warehouse, que localiza produtos por armazém
 */
@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    //método para pesquisar o id de produto e retornar a soma dos produtos por warehouse
    @GetMapping
    public ResponseEntity<List<WarehouseProductDTO>> listProductWarehouse(@RequestParam(required = false,
            name = "productId") Long id) {
        List<WarehouseProductDTO> findWarehouseByProducts = warehouseService.findWarehouse(id);
        return ResponseEntity.ok().body(findWarehouseByProducts);
    }


}

