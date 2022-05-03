package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderResponseDTO;
import com.mercadolibre.grupo1.projetointegrador.services.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
@author Gabriel Essenio
* Controller de Product ,cria os endpoints e trata o retorno de acordo com cada tipo de endpoint
 */

@RestController
@RequestMapping("api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    // Fazendo injeçao de dependencia do service InboundOrder
    @Autowired
    private InboundOrderService inboundOrderService;

    // Endpoint para fazer o cadastro de uma entrada de Ordem de Serviço
    @PostMapping
    public ResponseEntity<List<BatchStockDTO>> createInboundOrder(@Valid @RequestBody InboundOrderDTO inboundOrder, HttpServletRequest http){
        InboundOrderResponseDTO newInboundOrder = inboundOrderService.createInboundOrder(inboundOrder);
        return ResponseEntity.created(URI.create(http.getRequestURI() + "/" + newInboundOrder.getOrderNumber())).body(newInboundOrder.getBatchStocks());
    }

    // Endpoint para fazer a atualizaçao do Estoque pelo numero da Ordem de Serviço, pegando por parametro o Id que vai ser alterado
    @PutMapping("/{id}")
    public ResponseEntity<List<BatchStockDTO>> updateInboundOrder(@PathVariable Long id ,@Valid @RequestBody InboundOrderDTO inboundOrder, HttpServletRequest http){
        InboundOrderResponseDTO updateOrder = inboundOrderService.updateOrder(id, inboundOrder);
        return ResponseEntity.created(URI.create(http.getRequestURI())).body(updateOrder.getBatchStocks());
    }

    /**
     * Endpoint para listagem de estoque a partir do id da ordem de entrada
     * @param id
     * @author Weverton Bruno
     */
    @GetMapping("/{id}")
    public ResponseEntity<InboundOrderDTO> getInboundOrder(@PathVariable Long id){
        return ResponseEntity.ok(
                InboundOrderDTO.fromInboundOrder(inboundOrderService.findById(id))
        );
    }
}
