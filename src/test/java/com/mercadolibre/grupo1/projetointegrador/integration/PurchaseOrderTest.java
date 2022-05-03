package com.mercadolibre.grupo1.projetointegrador.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseOrderTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("verifica se os dados estao sendo retornados corretamente")
    public void showProductsInOrdersTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders/{idOrder}", 1))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].product.nome").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].product.price").value("100.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].product.category").value("FRESCO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].quantity").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value("2015-08-04T10:11:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").value("2022-04-26T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(3));
    }

    @Test
    @DisplayName("Testa se retorna error 404 se o id informado estiver incorreto")
    public void showProductsInOrdersExceptionTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/orders/{idOrders}", 8))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Pedido nao encontrado")));
    }

}


