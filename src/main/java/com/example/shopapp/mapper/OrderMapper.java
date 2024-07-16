package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.OrderCreationRequest;
import com.example.shopapp.dtos.request.OrderUpdateRequest;
import com.example.shopapp.dtos.response.OrderResponse;
import com.example.shopapp.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "totalMoney", target = "totalMoney")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Order orderToOrder(OrderCreationRequest request);
    OrderResponse orderToOrderResponse(Order order);
    @Mapping(source = "totalMoney", target = "totalMoney")
    @Mapping(target = "user", ignore = true)
    void orderToUpdateOrder(@MappingTarget Order order, OrderUpdateRequest request);
}
