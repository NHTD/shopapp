package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.OrderCreationRequest;
import com.example.shopapp.dtos.request.OrderUpdateRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.OrderListResponse;
import com.example.shopapp.dtos.response.OrderResponse;
import com.example.shopapp.enums.OrderStatusEnum;
import com.example.shopapp.mapper.OrderMapper;
import com.example.shopapp.models.Order;
import com.example.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @PreAuthorize("hasAnyAuthority('POST_DATA')")
    EntityResponse<OrderResponse> create(@RequestBody @Valid OrderCreationRequest request){
        return EntityResponse.<OrderResponse>builder()
                .status(true)
                .body(orderService.createOrder(request, OrderStatusEnum.PENDING))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_DATA')")
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

    @GetMapping("/get-orders-by-keyword")
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<OrderListResponse> getOrdersByKeyword(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").ascending()
        );

        Page<OrderResponse> orderPage = orderService
                .getOrdersByKeyword(keyword, pageRequest);

        int totalPages = orderPage.getTotalPages();

        List<OrderResponse> orderResponses = orderPage.getContent();

        return EntityResponse.<OrderListResponse>builder()
                .status(true)
                .body(OrderListResponse.builder().orders(orderResponses).totalPages(totalPages).build())
                .build();
    }

}
