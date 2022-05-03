package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.dtos.PurchaseOrderDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class PurchaseOrderTest {

    /**
     * @author Jefferson Botelho
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * @author Jefferson
     */
    @Test
    @DisplayName("verifica se os dados estao sendo retornados corretamente")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void showProductsInOrdersTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders/{idOrder}", 1))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].product.name").value("UVA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].product.price").value("10.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].product.category").value("FRESCO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].quantity").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value("2022-05-01T00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").value("2022-05-01T00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(2));
    }

    /**
     * @author Jefferson
     */
    @Test
    @DisplayName("Testa se retorna error 404 se o id informado estiver incorreto")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void showProductsInOrdersExceptionTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/orders/{idOrders}", 8))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Pedido nao encontrado")));
    }

    /**
     * @author Gabriel Essenio
     * @throws Exception
     * Testa o caminho feliz apos uma compra ser realizada
     */
    @Test
    @DisplayName("Testando endpoint muda o status de OPEN para CLOSE")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
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
    @Test
    @DisplayName("Testando enpoint quando tenta alterar status de compra qe nao existe retorna um status 404 e uma menssagem de erro")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void TestReturnStatus404WhenIdProductOrderDontExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/fresh-products/orders/0/close"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Pedido nao encontrado")));
    }

    /**
     * @author Gabriel Essenio
     * Testa se retorna um erro 403 de falta de permissao
     */
    @Test
    @DisplayName("Testando se o tipo de usuario é um Cliente")
    @WithMockUser(username = "seller1", roles = {"SELLER"})
    public void TestRoleUserIsntClient() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/fresh-products/orders/1/close"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Acesso não autorizado!")));
    }

    /**
     * @author Ederson Rodrigues
     */
    private PurchaseOrderDTO createPurchaseOrderDTO() {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        PurchaseOrderDTO.PurchaseOrder purchaseOrder = new PurchaseOrderDTO.PurchaseOrder();
        List<PurchaseOrderDTO.ProductItemDTO> products = new ArrayList<>();

        PurchaseOrderDTO.ProductItemDTO productItemDTO1 = PurchaseOrderDTO.ProductItemDTO.builder().productId(1L).quantity(10).build();

        products.add(productItemDTO1);

        purchaseOrderDTO.setPurchaseOrder(purchaseOrder);

        purchaseOrder.setBuyerId(5L);

        purchaseOrder.setProducts(products);

        return purchaseOrderDTO;
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com sucesso.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderSuccessful() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();

        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.totalPrice", Matchers.is(100.))
                ).andReturn();
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com userId null.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderExceptionNullUserId() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        purchaseOrderDTO.getPurchaseOrder().setBuyerId(null);

        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.message", Matchers.is("BuyerId não é permitido valor nulo!"))
                ).andReturn();
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com userId não cadastrado.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderExceptionUserNotRegistred() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        purchaseOrderDTO.getPurchaseOrder().setBuyerId(0L);

        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.message", Matchers.is("Usuário não cadastrado!"))
                ).andReturn();
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com productId não cadastrado.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderExceptionProductNotRegistred() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        purchaseOrderDTO.getPurchaseOrder().getProducts().get(0).setProductId(0L);

        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.message", Matchers.is("Produto não cadastrado!"))
                ).andReturn();
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com productId null.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderExceptionProductNull() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        purchaseOrderDTO.getPurchaseOrder().getProducts().get(0).setProductId(null);


        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.message", Matchers.is("ProductId não é permitido valor nulo!"))
                ).andReturn();
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com productId com quantidade zero.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderExceptionProductQuantityZero() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        purchaseOrderDTO.getPurchaseOrder().getProducts().get(0).setQuantity(0);


        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.message", Matchers.is("Quantidade mínima permitida: 1"))
                ).andReturn();
    }

    /**
     * @author Ederson Rodrigues
     */
    @Test
    @DisplayName("Testando cadastrar um Purchase Order com productId com quantidade null.")
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testPostPurchaseOrderExceptionProductQuantityNull() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO = createPurchaseOrderDTO();
        purchaseOrderDTO.getPurchaseOrder().getProducts().get(0).setQuantity(null);


        String payloadPurchaseOrder = objectMapper.writeValueAsString(purchaseOrderDTO);

        // Requisição
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON).content(payloadPurchaseOrder))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.message", Matchers.is("Quantidade não é permitido valor nulo!"))
                ).andReturn();
    }

}


