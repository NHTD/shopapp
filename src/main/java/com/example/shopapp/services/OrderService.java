package com.example.shopapp.services;

import com.example.shopapp.dtos.request.OrderCreationRequest;
import com.example.shopapp.dtos.request.OrderUpdateRequest;
import com.example.shopapp.dtos.response.OrderResponse;
import com.example.shopapp.enums.OrderStatusEnum;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreationRequest request, OrderStatusEnum orderStatusEnum);
    OrderResponse getOrder(Long id);
    OrderResponse updateOrder(Long id, OrderUpdateRequest request);
    void deleteOrder(Long id);
    List<OrderResponse> getOrders();
}
