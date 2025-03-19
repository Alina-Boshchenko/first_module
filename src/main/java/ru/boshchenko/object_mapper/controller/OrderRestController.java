package ru.boshchenko.object_mapper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.object_mapper.model.Order;
import ru.boshchenko.object_mapper.service.OrderService;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody String data) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(data);
        Order order = objectMapper.treeToValue(jsonNode, Order.class);
        String customerId = jsonNode.get("customer_id").asText();
        List<String> productIds = objectMapper.readValue(
                jsonNode.get("products_id").traverse(),
                new TypeReference<List<String>>() {});
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Order> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Order orderSaved = orderService.create(order, customerId, productIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderSaved.getId());
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllOrder() throws JsonProcessingException {
        List<Order> orders = orderService.findAll();
        String data = objectMapper.writeValueAsString(orders);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable UUID id) throws JsonProcessingException {
        Order order = orderService.findById(id);
        String data = objectMapper.writeValueAsString(order);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody String data) throws JsonProcessingException {
        Order order = objectMapper.readValue(data, Order.class);
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Order> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Order orderUp = orderService.update(id, order);
        return ResponseEntity.ok(orderUp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        orderService.deleteById(id);
        return ResponseEntity.ok("delete");
    }

}
