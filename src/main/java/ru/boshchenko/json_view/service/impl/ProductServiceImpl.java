package ru.boshchenko.json_view.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.json_view.dto.request.ProductRequest;
import ru.boshchenko.json_view.model.Product;
import ru.boshchenko.json_view.repository.ProductRepository;
import ru.boshchenko.json_view.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        return productRepository.save(product);
    }
}
