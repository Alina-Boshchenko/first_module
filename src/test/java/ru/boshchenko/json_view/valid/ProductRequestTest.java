package ru.boshchenko.json_view.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import ru.boshchenko.json_view.dto.request.ProductRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductRequestTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private ProductRequest productRequest;

    @BeforeEach
    public void setUp() {
        productRequest = new ProductRequest();
        productRequest.setName("Laptop");
    }

    @Test
    public void testValidProductRequest() {
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testEmptyName() {
        productRequest.setName("");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertEquals(1, violations.size());
        assertEquals("name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullName() {
        productRequest.setName(null);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertEquals(1, violations.size());
        assertEquals("name is required", violations.iterator().next().getMessage());
    }
}