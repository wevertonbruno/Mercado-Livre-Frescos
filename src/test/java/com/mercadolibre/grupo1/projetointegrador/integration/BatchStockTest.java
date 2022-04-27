package com.mercadolibre.grupo1.projetointegrador.integration;

import com.mercadolibre.grupo1.projetointegrador.dtos.BatchStockDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.SectionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BatchStockTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void itShouldPass(){

    }

    public InboundOrderDTO createPayload(){
        InboundOrderDTO input = new InboundOrderDTO();
        SectionDTO sectionDTO = new SectionDTO();
        return input;
    }

    public BatchStockDTO createBatchStock(){
        BatchStockDTO batch = new BatchStockDTO();
        batch.setBatchNumber(null);
        batch.setInitialQuantity(10);
        batch.setCurrentQuantity(10);
        batch.setMinimumTemperature(10F);
        batch.setCurrentTemperature(10F);
        batch.setProductId(1L);
        batch.setManufacturingDateTime(LocalDateTime.parse("2022-04-15T00:00:00"));
        batch.setDueDate(LocalDate.parse("2022-05-15"));

        return batch;
    }
}
