package ru.boshchenko.json_view.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.json_view.dto.request.OrderRequest;
import ru.boshchenko.json_view.dto.response.OrderResponse;
import ru.boshchenko.json_view.exception.ResourceNotFoundException;
import ru.boshchenko.json_view.mapper.OrderMapper;
import ru.boshchenko.json_view.model.Order;
import ru.boshchenko.json_view.model.Product;
import ru.boshchenko.json_view.model.User;
import ru.boshchenko.json_view.repository.OrderRepository;
import ru.boshchenko.json_view.repository.ProductRepository;
import ru.boshchenko.json_view.repository.UserRepository;
import ru.boshchenko.json_view.service.OrderService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper = new OrderMapper();

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        List<Product> products = productRepository.findAllById(orderRequest.getProductsId());
        order.setProducts(products);
        user.getOrders().add(order);
        order.setUser(user);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse findById(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }
}
