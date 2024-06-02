package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.OrderDetailRequest;
import com.example.shopapp.dtos.response.OrderDetailResponse;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.OrderDetailMapper;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.services.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImp implements OrderDetailService {

    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Order with id {} is not found", request.getOrderId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Product with id {} is not found", request.getProductId()));

        OrderDetail orderDetail = orderDetailMapper.orderToOrder(request);

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        return orderDetailMapper.orderToOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Order details with id {} is not found", id));

        return orderDetailMapper.orderToOrderDetailResponse(orderDetail);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest request) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Order details with id {} is not found", id));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Order with id {} is not found", request.getOrderId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Product with id {} is not found", request.getProductId()));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        orderDetailMapper.OrderToUpdateOrder(orderDetail, request);

        return orderDetailMapper.orderToOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    @Override
    public void deleteOrderDetail(Long id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        if(orderDetail.isEmpty()){
            throw new ShopAppModelsNotFoundException("Order details with id {} is not found", id);
        }
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(orderDetailMapper::orderToOrderDetailResponse)
                .collect(Collectors.toList());
    }
}
