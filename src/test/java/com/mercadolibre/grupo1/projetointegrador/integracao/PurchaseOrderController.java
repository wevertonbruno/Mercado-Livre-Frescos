package com.mercadolibre.grupo1.projetointegrador.integracao;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

/**
 * @author Gabriel Essenio
 * teste de integração de products
 */
@AutoConfigureMockMvc
@SpringBootTest
public class PurchaseOrderController {

    @Autowired
    private MockMvc mockMvc;

    /**
     * @author Gabriel Essenio
     * @throws Exception
     * Testa o caminho feliz apos uma compra ser realizada
     */
    @Transactional
    @Test
    @DisplayName("Testando endpoint muda o status de OPEN para CLOSE")
    public void TestStatusChangeWhen() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .put("/api/v1/fresh-products/orders/1/close"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus", Matchers.is("CLOSED")));
    }

    /**
     * @author Gabriel Essenio
     * @throws Exception
     * Testa se retorna um erro 404 do controller quando tentar
     */
    @Transactional
    @Test
    @DisplayName("Testando enpoint quando tenta alterar status de compra qe nao existe retorna um status 404 e uma menssagem de erro")
    public void TestReturnStatus404WhenIdProductOrderDontExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/fresh-products/orders/0/close"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Pedido nao encontrado")));
    }
}
