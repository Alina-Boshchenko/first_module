package ru.boshchenko.object_mapper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.boshchenko.object_mapper.model.Order;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {

    Order create(Order order, String customerId, List<String> productIds);

    List<Order> findAll();

    Order findById(UUID id);

    Order update(UUID id, Order order);

    void deleteById(UUID id);

}
