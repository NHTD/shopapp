package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.OrderDetailRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.OrderDetailResponse;
import com.example.shopapp.services.OrderDetailService;
import com.example.shopapp.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order_details")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {

    OrderDetailService orderDetailService;

    @PostMapping
    EntityResponse<OrderDetailResponse> createOrderDetail(@RequestBody OrderDetailRequest request){
        return EntityResponse.<OrderDetailResponse>builder()
                .status(true)
                .body(orderDetailService.createOrderDetail(request))
                .build();
    }

    @GetMapping("/{id}")
    EntityResponse<OrderDetailResponse> getOrderDetail(@PathVariable("id") Long id){
        return EntityResponse.<OrderDetailResponse>builder()
                .status(true)
                .body(orderDetailService.getOrderDetail(id))
                .build();
    }

    @PutMapping("/{id}")
    EntityResponse<OrderDetailResponse> updateOrderDetail(
            @PathVariable("id") Long id,
            @RequestBody OrderDetailRequest request
    ){
        return EntityResponse.<OrderDetailResponse>builder()
                .status(true)
                .body(orderDetailService.updateOrderDetail(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    EntityResponse<String> deleteOrderDetail(
            @PathVariable("id") Long id
    ){
        orderDetailService.deleteOrderDetail(id);
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete is successful")
                .build();
    }
}
