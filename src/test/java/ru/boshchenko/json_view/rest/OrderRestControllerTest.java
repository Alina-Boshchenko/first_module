package ru.boshchenko.json_view.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.json_view.controller.OrderRestController;
import ru.boshchenko.json_view.dto.request.OrderRequest;
import ru.boshchenko.json_view.service.OrderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.Collections;

@WebMvcTest(OrderRestController.class)
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /create валидирует OrderRequest")
    void createOrder_ShouldValidateInput() throws Exception {
        OrderRequest invalidRequest = new OrderRequest();
        invalidRequest.setAmount(BigDecimal.valueOf(-1));
        invalidRequest.setProductsId(Collections.emptyList());
        invalidRequest.setUserId(null);

        mockMvc.perform(post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.amount").value("amount must be at least 0.01"))
                .andExpect(jsonPath("$.productsId").value("productsId is empty"))
                .andExpect(jsonPath("$.userId").value("userId is required"));
    }
}
