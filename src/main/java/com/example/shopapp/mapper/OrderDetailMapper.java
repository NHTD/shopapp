package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.OrderDetailRequest;
import com.example.shopapp.dtos.response.OrderDetailResponse;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderDetail orderToOrder(OrderDetailRequest request);
    OrderDetailResponse orderToOrderDetailResponse(OrderDetail orderDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void OrderToUpdateOrder(@MappingTarget OrderDetail orderDetail, OrderDetailRequest request);
}
