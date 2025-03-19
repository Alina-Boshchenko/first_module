package ru.boshchenko.object_mapper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.object_mapper.model.Product;
import ru.boshchenko.object_mapper.service.ProductService;

import java.util.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody String data) throws JsonProcessingException {
        Product product = objectMapper.readValue(data, Product.class);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Product> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Product productSaved = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved.getId());
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllProduct() throws JsonProcessingException {
        List<Product> products = productService.findAll();
        String data = objectMapper.writeValueAsString(products);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable UUID id) throws JsonProcessingException {
        Product product = productService.findById(id);
        String data = objectMapper.writeValueAsString(product);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody String data) throws JsonProcessingException {
        Product product = objectMapper.readValue(data, Product.class);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Product> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Product productUp = productService.update(id, product);
        return ResponseEntity.ok(productUp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.ok("delete");
    }


}
