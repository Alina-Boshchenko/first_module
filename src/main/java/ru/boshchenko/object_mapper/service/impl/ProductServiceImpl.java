package ru.boshchenko.object_mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.boshchenko.object_mapper.exception.ResourceNotFoundException;
import ru.boshchenko.object_mapper.model.Product;
import ru.boshchenko.object_mapper.repo.ProductRepo;
import ru.boshchenko.object_mapper.service.ProductService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public Product create(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(UUID id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product update(UUID id, Product product) {
        Product productOld = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getName() != null) {
            productOld.setName(product.getName());
        }
        if (product.getDescription() != null) {
            productOld.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            productOld.setPrice(product.getPrice());
        }
        if (product.getQuantityInStock() != null) {
            productOld.setQuantityInStock(product.getQuantityInStock());
        }
        return productRepo.save(productOld);
    }

    @Override
    public void deleteById(UUID id) {
        productRepo.deleteById(id);
    }
}
