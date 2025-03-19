package ru.boshchenko.object_mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.object_mapper.controller.ProductRestController;
import ru.boshchenko.object_mapper.model.Product;
import ru.boshchenko.object_mapper.service.ProductService;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private Validator validator;

    @Test
    @DisplayName("POST /create - Успешное создание продукта")
    void createProduct() throws Exception {
        String validJson = """
                {
                     "name": "rgwe",
                     "description": "awfeg qewrughfweug fdqerf we",
                     "price": 3000.00,
                     "quantity_in_stock": 20,
                     "test": "dwdw",
                     "test2": "dwadw"
                 }""";
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        product.setName("Laptop");
        product.setPrice(new BigDecimal("1500.00"));

        Mockito.when(validator.validate(any(Product.class))).thenReturn(Collections.emptySet());
        Mockito.when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + productId.toString() + "\""));
    }

    @Test
    @DisplayName("POST /create - Невалидные данные продукта")
    void createProductInvalidRequest() throws Exception {
        String invalidJson = """
                {
                    "name": "",
                    "price": -100.00
                }""";

        ConstraintViolation<Product> violation = Mockito.mock(ConstraintViolation.class);
        Path path = Mockito.mock(Path.class);

        Mockito.when(violation.getPropertyPath()).thenReturn(path);
        Mockito.when(violation.getMessage()).thenReturn("Name is required");
        Mockito.when(path.toString()).thenReturn("name");
        Mockito.when(validator.validate(any(Product.class))).thenReturn(Set.of(violation));
        mockMvc.perform(post("/api/product/create").contentType(MediaType.APPLICATION_JSON).content(invalidJson)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.name").value("Name is required"));
    }

    @Test
    @DisplayName("GET /all - Получение всех продуктов")
    void getAllProducts() throws Exception {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        List<Product> products = List.of(product);
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/api/product/all")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(product.getId().toString()));
    }

    @Test
    @DisplayName("GET /{id} - Получение продукта по ID")
    void getProductById() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);

        Mockito.when(productService.findById(productId)).thenReturn(product);
        mockMvc.perform(get("/api/product/{id}", productId)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(productId.toString()));
    }

    @Test
    @DisplayName("DELETE /{id} - Удаление продукта")
    void deleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        mockMvc.perform(delete("/api/product/{id}", productId)).andExpect(status().isOk()).andExpect(content().string("delete"));
        Mockito.verify(productService).deleteById(productId);
    }
}
