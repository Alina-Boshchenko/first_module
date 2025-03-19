package ru.boshchenko.object_mapper;

import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.object_mapper.controller.OrderRestController;
import ru.boshchenko.object_mapper.model.Order;
import ru.boshchenko.object_mapper.service.OrderService;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderRestController.class)
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private Validator validator;

    @Test
    @DisplayName("POST /create - Успешное создание заказа")
    void createOrder() throws Exception {
        String validJson = """
                {
                    "order_date": "2025-01-01",
                    "total_price": 1000.00,
                    "customer_id": "a3516bce-d021-4a8b-bef6-948dc6d901d7",
                    "products_id": ["33f61839-6509-491a-a5ba-48706ac0cdf1"]
                }""";

        UUID orderId = UUID.randomUUID();
        Order mockOrder = new Order();
        mockOrder.setId(orderId);

        Mockito.when(validator.validate(any(Order.class)))
                .thenReturn(Collections.emptySet());
        Mockito.when(orderService.create(any(Order.class), anyString(), anyList()))
                .thenReturn(mockOrder);

        mockMvc.perform(post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + orderId.toString() + "\""));
    }

    @Test
    @DisplayName("GET /all - Получение всех заказов")
    void getAllOrders() throws Exception {
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        List<Order> orders = List.of(order1);

        Mockito.when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get("/api/order/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order1.getId().toString()));
    }

    @Test
    @DisplayName("GET /{id} - Получение заказа по ID")
    void getOrderById_Exists_ReturnsOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);

        Mockito.when(orderService.findById(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/order/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()));
    }

    @Test
    @DisplayName("DELETE /{id} - Удаление заказа")
    void deleteOrder_Exists_ReturnsOk() throws Exception {
        UUID orderId = UUID.randomUUID();

        mockMvc.perform(delete("/api/order/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().string("delete"));

        Mockito.verify(orderService).deleteById(orderId);
    }
}
