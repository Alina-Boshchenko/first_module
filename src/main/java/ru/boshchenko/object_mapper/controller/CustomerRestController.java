package ru.boshchenko.object_mapper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.boshchenko.object_mapper.model.Customer;
import ru.boshchenko.object_mapper.service.CustomerService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody String data) throws JsonProcessingException {
        Customer customer = objectMapper.readValue(data, Customer.class);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Customer> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Customer customerSaved = customerService.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerSaved.getId());
    }

}
