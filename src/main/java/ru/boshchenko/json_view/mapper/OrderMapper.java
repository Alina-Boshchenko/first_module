package ru.boshchenko.json_view.mapper;

import ru.boshchenko.json_view.dto.request.OrderRequest;
import ru.boshchenko.json_view.dto.response.OrderResponse;
import ru.boshchenko.json_view.model.Order;

public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setAmount(orderRequest.getAmount());
        order.setOrderStatus(orderRequest.getOrderStatus());
        return order;
    }

    public OrderResponse toOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setAmount(order.getAmount());
        orderResponse.setOrderStatus(order.getOrderStatus());
        return orderResponse;
    }

}
