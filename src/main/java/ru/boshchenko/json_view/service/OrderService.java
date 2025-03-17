package ru.boshchenko.json_view.service;

import org.springframework.stereotype.Service;
import ru.boshchenko.json_view.dto.request.OrderRequest;
import ru.boshchenko.json_view.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {

    OrderResponse save(OrderRequest orderRequest);
    OrderResponse findById(UUID id);
    void deleteById(UUID id);
    List<OrderResponse> findAll();

}
