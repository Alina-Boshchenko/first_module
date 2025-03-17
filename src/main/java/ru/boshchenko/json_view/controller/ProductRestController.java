package ru.boshchenko.json_view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.boshchenko.json_view.dto.request.ProductRequest;
import ru.boshchenko.json_view.model.Product;
import ru.boshchenko.json_view.service.ProductService;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest){
        Product productSave = productService.save(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSave);
    }

}
