package ru.boshchenko.json_view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.json_view.dto.request.OrderRequest;
import ru.boshchenko.json_view.dto.response.OrderResponse;
import ru.boshchenko.json_view.service.OrderService;


@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.save(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
