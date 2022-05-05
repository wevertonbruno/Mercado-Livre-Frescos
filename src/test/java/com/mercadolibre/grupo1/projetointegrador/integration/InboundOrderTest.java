package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe de testes de integracao para o InboundOrder
 * @author Weverton Bruno
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class InboundOrderTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "http://localhost:8080/api/v1/fresh-products/inboundorder";

    @Test
    @WithMockUser(username = "agent2", roles = {"AGENT"})
    @DisplayName("Testa se uma ordem de entrada é salva com todos os dados validos")
    public void itShouldSaveAInboundOrder() throws Exception {
        InboundOrderDTO order = createFakeOrderInput();
        BatchStockDTO batchStock1 = createFakeBatchStockInput(null, 1L);
        BatchStockDTO batchStock2 = createFakeBatchStockInput(null, 2L);

        order.getSection().setSectionCode(4L);
        order.getSection().setWarehouseCode(2L);
        order.getBatchStock().addAll(Arrays.asList(batchStock1, batchStock2));

        String payload = objectMapper.writeValueAsString(order);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/fresh-products/inboundorder/9"))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    @WithMockUser(username = "agent1", roles = {"AGENT"})
    @DisplayName("Testa se uma ordem de entrada existente é atualizada")
    public void itShouldUpdateAInboundOrder() throws Exception {
        InboundOrderDTO order = createFakeOrderInput();
        BatchStockDTO batchStock1 = createFakeBatchStockInput(1L, 1L);
        BatchStockDTO batchStock2 = createFakeBatchStockInput(2L, 2L);
        BatchStockDTO batchStock3 = createFakeBatchStockInput(null, 3L);

        order.getSection().setSectionCode(1L);
        order.getSection().setWarehouseCode(1L);
        order.getBatchStock().addAll(Arrays.asList(batchStock1, batchStock2, batchStock3));

        String payload = objectMapper.writeValueAsString(order);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/fresh-products/inboundorder/1"))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    @WithMockUser(username = "agent1", roles = {"AGENT"})
    @DisplayName("Testa se uma ordem de entrada existente é buscada pelo ID")
    public void itShouldGetAINboundOrder() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "agent1", roles = {"AGENT"})
    @DisplayName("Testa se um bad request é retornado ao tentar inserir um volume de lotes maior que a capacidade da sessao")
    public void itShouldGetABadRequestOvercapacity() throws Exception {
        InboundOrderDTO order = createFakeOrderInput();
        BatchStockDTO batchStock1 = createFakeBatchStockInput(1L, 1L);
        BatchStockDTO batchStock2 = createFakeBatchStockInput(2L, 2L);
        BatchStockDTO batchStock3 = createFakeBatchStockInput(null, 3L);
        batchStock3.setCurrentQuantity(25);

        order.getSection().setSectionCode(1L);
        order.getSection().setWarehouseCode(1L);
        order.getBatchStock().addAll(Arrays.asList(batchStock1, batchStock2, batchStock3));

        String payload = objectMapper.writeValueAsString(order);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Capacidade da sessao excedida! Capacidade total da sessao: 500,00m3, Capacidade ocupada: 450,00m3, Após a operacao: 525,00m3.")));
    }

    @Test
    @WithMockUser(username = "agent1", roles = {"AGENT"})
    @DisplayName("Testa se um bad request é retornado ao tentar inserir um lote cuja categoria seja diferente da categoria aceita na sessão")
    public void itShouldGetABadRequestInvalidCategory() throws Exception {
        InboundOrderDTO order = createFakeOrderInput();
        BatchStockDTO batchStock1 = createFakeBatchStockInput(1L, 1L);
        BatchStockDTO batchStock2 = createFakeBatchStockInput(2L, 2L);
        BatchStockDTO batchStock3 = createFakeBatchStockInput(null, 4L);

        order.getSection().setSectionCode(1L);
        order.getSection().setWarehouseCode(1L);
        order.getBatchStock().addAll(Arrays.asList(batchStock1, batchStock2, batchStock3));

        String payload = objectMapper.writeValueAsString(order);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Só é permitido produtos da categoria FRESCO nesta sessão.")));
    }

    @Test
    @WithMockUser(username = "agent1", roles = {"AGENT"})
    @DisplayName("Testa se um bad request é retornado caso se tente atualizar um lote já existente de outra ordem de entrada")
    public void itShouldGetABadRequestInvalidOperation() throws Exception {
        InboundOrderDTO order = createFakeOrderInput();
        BatchStockDTO batchStock1 = createFakeBatchStockInput(1L, 1L);
        BatchStockDTO batchStock2 = createFakeBatchStockInput(4L, 2L);

        order.getSection().setSectionCode(1L);
        order.getSection().setWarehouseCode(1L);
        order.getBatchStock().addAll(Arrays.asList(batchStock1, batchStock2));

        String payload = objectMapper.writeValueAsString(order);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("O lote de ID 4 não pertence a esta Ordem de Entrada!")));
    }

    @Test
    @WithMockUser(username = "agent1", roles = {"AGENT"})
    @DisplayName("Testa se um bad request é retornado caso se tente atualizar um lote que não existe")
    public void itShouldGetABadRequestInvalidOperationBatchNotFound() throws Exception {
        InboundOrderDTO order = createFakeOrderInput();
        BatchStockDTO batchStock1 = createFakeBatchStockInput(1L, 1L);
        BatchStockDTO batchStock2 = createFakeBatchStockInput(-1L, 2L);

        order.getSection().setSectionCode(1L);
        order.getSection().setWarehouseCode(1L);
        order.getBatchStock().addAll(Arrays.asList(batchStock1, batchStock2));

        String payload = objectMapper.writeValueAsString(order);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Lote com ID -1 não encontrado.")));
    }

    private InboundOrderDTO createFakeOrderInput(){
        InboundOrderDTO inboundOrderDTO = new InboundOrderDTO();
        SectionDTO sectionDTO = new SectionDTO();

        inboundOrderDTO.setOrderDate(LocalDate.parse("2022-01-01"));
        inboundOrderDTO.setSection(sectionDTO);
        inboundOrderDTO.setBatchStock(new ArrayList<>());

        return inboundOrderDTO;
    }

    private BatchStockDTO createFakeBatchStockInput(Long batchNumber, Long productId){
        BatchStockDTO item = new BatchStockDTO();
        item.setBatchNumber(batchNumber);
        item.setInitialQuantity(5);
        item.setCurrentQuantity(5);
        item.setCurrentTemperature(10.0F);
        item.setMinimumTemperature(5.0F);
        item.setDueDate(LocalDate.parse("2022-10-01"));
        item.setManufacturingDateTime(LocalDateTime.parse("2022-01-01T00:00:00"));
        item.setProductId(productId);

        return item;
    }

}
