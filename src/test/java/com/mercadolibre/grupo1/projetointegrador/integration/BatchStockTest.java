package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class BatchStockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "http://localhost:8080/api/v1/fresh-products";

    @Test
    public void itShouldReturnAllBatchesBySectionAndDueDateBetween() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(BASE_URL + "/due-date?section_code=1&expires_in=15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andReturn();

        List<BatchStockDTO.SimpleBatchStockDTO> finalResult = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), BatchStockDTO.SimpleBatchStockDTO[].class));
        assertTrue(finalResult.stream().allMatch(item -> item.getCategory().equals(ProductCategory.FRESCO)));
    }

    @Test
    public void itShouldReturnAllBatchesByCategoryAndDueDateBetweenSorted() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(BASE_URL + "/due-date/list?category=CONGELADO&expires_in=15&direction=ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andReturn();

        List<BatchStockDTO.SimpleBatchStockDTO> finalResult = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), BatchStockDTO.SimpleBatchStockDTO[].class));
        assertTrue(finalResult.stream().allMatch(item -> item.getCategory().equals(ProductCategory.CONGELADO)));
    }

}
