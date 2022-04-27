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
/*
    @author Gabriel Essenio
 */

//Fazendo anotaçao do controller e criando endereço do endpoint
@RestController
@RequestMapping("api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    // Fazendo injeçao de dependencia do service InboundOrder
    @Autowired
    private InboundOrderService inboundOrderService;

    // Endpoint para fazer o cadastro de uma entrada de Ordem de Serviço
    @PostMapping
    public ResponseEntity<InboundOrderResponseDTO> createInboundOrder(@Valid @RequestBody InboundOrderDTO inboundOrder, HttpServletRequest http){
        InboundOrderResponseDTO newInboundOrder = inboundOrderService.createInboundOrder(inboundOrder);
        return ResponseEntity.created(URI.create(http.getRequestURI())).body(newInboundOrder);
    }

    // Endpoint para fazer a atualizaçao do Estoque pelo numero da Ordem de Serviço, pegando por parametro o Id que vai ser alterado
    @PutMapping("/{id}")
    public ResponseEntity<InboundOrderResponseDTO> updateInboundOrder(@PathVariable Long id ,@Valid @RequestBody InboundOrderDTO inboundOrder, HttpServletRequest http){
        InboundOrderResponseDTO newInboundOrder = inboundOrderService.updateOrder(id, inboundOrder);
        return ResponseEntity.created(URI.create(http.getRequestURI())).body(newInboundOrder);
    }
}
