package ru.boshchenko.object_mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.boshchenko.object_mapper.exception.ResourceNotFoundException;
import ru.boshchenko.object_mapper.model.Customer;
import ru.boshchenko.object_mapper.model.Order;
import ru.boshchenko.object_mapper.model.Product;
import ru.boshchenko.object_mapper.repo.CustomerRepo;
import ru.boshchenko.object_mapper.repo.OrderRepo;
import ru.boshchenko.object_mapper.repo.ProductRepo;
import ru.boshchenko.object_mapper.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;

    @Override
    public Order create(Order order, String customerId, List<String> productIds) {
        Customer customer = customerRepo.findById(UUID.fromString(customerId)).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        List<Product> products = productIds.stream()
                .map(UUID::fromString)
                .map(productRepo::findById)
                .map(el -> el.orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                .toList();
        order.setCustomer(customer);
        order.setProducts(products);
        System.out.println(order);
        return orderRepo.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(UUID id) {
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public Order update(UUID id, Order order) {
        Order orderOld = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getOrderStatus() != null) {
            orderOld.setOrderStatus(order.getOrderStatus());
        }
        if (order.getOrderDate() != null) {
            orderOld.setOrderDate(order.getOrderDate());
        }
        if (order.getShippingAddress() != null) {
            orderOld.setShippingAddress(order.getShippingAddress());
        }
        if (order.getTotalPrice() != null) {
            orderOld.setTotalPrice(order.getTotalPrice());
        }
        return orderRepo.save(orderOld);
    }

    @Override
    public void deleteById(UUID id) {
        orderRepo.deleteById(id);
    }
}
