package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.OrderCreationRequest;
import com.example.shopapp.dtos.request.OrderUpdateRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.OrderResponse;
import com.example.shopapp.enums.OrderStatusEnum;
import com.example.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<OrderResponse> getOrder(@PathVariable("id") Long id){
        return EntityResponse.<OrderResponse>builder()
                .status(true)
                .body(orderService.getOrder(id))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<OrderResponse> update(@PathVariable("id") Long id, @RequestBody @Valid OrderUpdateRequest request){
        return EntityResponse.<OrderResponse>builder()
                .status(true)
                .body(orderService.updateOrder(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<String> delete(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete is successful")
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<List<OrderResponse>> getOrders(){
        return EntityResponse.<List<OrderResponse>>builder()
                .status(true)
                .body(orderService.getOrders())
                .build();
    }
}
