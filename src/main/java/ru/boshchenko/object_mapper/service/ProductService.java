package ru.boshchenko.object_mapper.service;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.boshchenko.object_mapper.model.Product;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    Product create(Product product);

    List<Product> findAll();

    Product findById(UUID id);

    Product update(UUID id, Product product);

    void deleteById(UUID id);

}
