package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.FindProductResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rogério Lambert
 * Testes de integração da rota do requisito 3 findProducts
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FindProductTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "http://localhost:8080/api/v1/fresh-products/list";

    @WithMockUser(username = "agent3")
    private void executeOkReturnsTest(String urlTest, List<Long> expectOrder, String productId) throws Exception {
        //Executa a requisição de todos os lotes de produto "productId" do representante 7 na warehouse 3 seção 7
        MvcResult result = mockMvc.perform(get(BASE_URL + urlTest))
                .andExpect(status().isOk())
                .andReturn();
        FindProductResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), FindProductResponseDTO.class);

        //Executa as aferições
        assertEquals(response.getSection().getSectionCode(), 7L);
        assertEquals(response.getSection().getWarehouseCode(), 3L);
        assertEquals(response.getBatchStock().size(), 3);
        assertEquals(response.getProductId(), productId);
        assertEquals(response.getBatchStock().get(0).getBatchNumber(), expectOrder.get(0));
        assertEquals(response.getBatchStock().get(1).getBatchNumber(), expectOrder.get(1));
        assertEquals(response.getBatchStock().get(2).getBatchNumber(), expectOrder.get(2));
    }

    @Test
    @WithMockUser(username = "agent3", roles = {"AGENT"})
    @DisplayName("Testa endpoint retona lista lotes de determinado produto " +
            " de determinado representate ordenado por id quando ordenacão não é especificada: ")
    public void itShouldReturnBatchListOrderById() throws Exception  {
        executeOkReturnsTest("/1", Arrays.asList(16L, 17L, 18L), "1");
    }

    @Test
    @WithMockUser(username = "agent3", roles = {"AGENT"})
    @DisplayName("Testa endpoint retona lista lotes de determinado produto " +
            " de determinado representate ordenado por quantidade quando ordenacão não é especificada: ")
    public void itShouldReturnBatchListOrderByQuantity() throws Exception  {
        executeOkReturnsTest("/1?type=C", Arrays.asList(17L, 18L, 16L), "1");
    }

    @Test
    @WithMockUser(username = "agent3", roles = {"AGENT"})
    @DisplayName("Testa endpoint retona lista lotes de determinado produto " +
            " de determinado representate ordenado por data de validade quando ordenacão não é especificada: ")
    public void itShouldReturnBatchListOrderByDueDate() throws Exception  {
        executeOkReturnsTest("/1?type=F", Arrays.asList(18L, 17L, 16L), "1");
    }

    @Test
    @WithMockUser(username = "agent3", roles = {"AGENT"})
    @DisplayName("Testa se os lotes do produto com data de validade menor que 21 dias não são trazidos na busca: ")
    public void itShouldReturnBatchListWithoutBatchesCloseToExpire() throws Exception  {
        //Executa a requisição de todos os lotes de produto 3 do representante 7 na warehouse 3 seção 8,
        // existem 3 lotes registrados nesta seção, porem um deles não é retornado por a data de validade é inferior a 21 dias
        MvcResult result = mockMvc.perform(get(BASE_URL + "/3"))
                .andExpect(status().isOk())
                .andReturn();
        FindProductResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), FindProductResponseDTO.class);

        //Executa as aferições
        assertEquals(response.getSection().getSectionCode(), 8L);
        assertEquals(response.getSection().getWarehouseCode(), 3L);
        assertEquals(response.getBatchStock().size(), 2);
        assertEquals(response.getProductId(), "3");
        assertEquals(response.getBatchStock().get(0).getBatchNumber(), 19L);
        assertEquals(response.getBatchStock().get(1).getBatchNumber(), 21L);
    }

    @Test
    @WithMockUser(username = "agent3", roles = {"AGENT"})
    @DisplayName("Testa se exceção ProductNotAvailable é lançada quando nenhum lote do produto é encontrado e se o erro 404 é retornado: ")
    public void itShouldReturn404WhenNoOneBatchIsFind() throws Exception  {
        //Executa a requisição de todos os lotes de produto 4 do representante 7 na warehouse 3,
        //porém todos os produtos registrados tem data de validade menor de 21 dias,
        //por isso deve ser retornado status 404 e mensagem de erro "Produto não disponível
        mockMvc.perform(get(BASE_URL + "/4"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Produto não disponível")));
    }
}
