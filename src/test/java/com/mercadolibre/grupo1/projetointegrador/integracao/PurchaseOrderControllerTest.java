package com.mercadolibre.grupo1.projetointegrador.integracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PurchaseOrderDTO createPurchaseOrderDTO() {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        PurchaseOrderDTO.PurchaseOrder purchaseOrder = new PurchaseOrderDTO.PurchaseOrder();
        List<PurchaseOrderDTO.ProductItemDTO> products = new ArrayList<>();

        PurchaseOrderDTO.ProductItemDTO productItemDTO1 = PurchaseOrderDTO.ProductItemDTO.builder().productId(1L).quantity(10).build();

        products.add(productItemDTO1);

        purchaseOrderDTO.setPurchaseOrder(purchaseOrder);

        purchaseOrder.setBuyerId(7L);

        purchaseOrder.setProducts(products);

        return purchaseOrderDTO;
    }

    @Test
    @DisplayName("Testando cadastrar um Purchase Order com sucesso")
    public void testPostPurchaseOrderSuccessful() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();

        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.totalPrice", Matchers.is(10.))
                ).andReturn();
    }

}
