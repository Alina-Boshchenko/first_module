package ru.boshchenko.json_view.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import ru.boshchenko.json_view.dto.request.OrderRequest;
import ru.boshchenko.json_view.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderRequestTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private OrderRequest orderRequest;

    @BeforeEach
    public void setUp() {
        orderRequest = new OrderRequest();
        orderRequest.setAmount(new BigDecimal("10.50"));
        orderRequest.setOrderStatus(OrderStatus.CREATED);
        orderRequest.setProductsId(List.of(UUID.randomUUID(), UUID.randomUUID()));
        orderRequest.setUserId(UUID.randomUUID());
    }

    @Test
    public void testValidOrderRequest() {
        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidAmount() {
        orderRequest.setAmount(new BigDecimal("-1.00"));
        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);
        assertEquals(1, violations.size());
        assertEquals("amount must be at least 0.01", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyProductsId() {
        orderRequest.setProductsId(List.of());
        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);
        assertEquals(1, violations.size());
        assertEquals("productsId is empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullUserId() {
        orderRequest.setUserId(null);
        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);
        assertEquals(1, violations.size());
        assertEquals("userId is required", violations.iterator().next().getMessage());
    }
}