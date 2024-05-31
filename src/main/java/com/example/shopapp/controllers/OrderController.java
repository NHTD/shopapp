package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.OrderCreationRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.OrderResponse;
import com.example.shopapp.enums.OrderStatusEnum;
import com.example.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<OrderResponse> create(@RequestBody @Valid OrderCreationRequest request){
        return EntityResponse.<OrderResponse>builder()
                .status(true)
                .body(orderService.createOrder(request, OrderStatusEnum.PENDING))
                .build();
    }
}
