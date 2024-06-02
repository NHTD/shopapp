package com.example.shopapp.services;

import com.example.shopapp.dtos.request.OrderDetailRequest;
import com.example.shopapp.dtos.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailRequest request);
    OrderDetailResponse getOrderDetail(Long id);
    OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest request);
    void deleteOrderDetail(Long id);
    List<OrderDetailResponse> getOrderDetails();
}
