package ru.boshchenko.json_view.service;

import org.springframework.stereotype.Service;
import ru.boshchenko.json_view.dto.request.ProductRequest;
import ru.boshchenko.json_view.model.Product;

@Service
public interface ProductService {

    Product save(ProductRequest productRequest);
}
