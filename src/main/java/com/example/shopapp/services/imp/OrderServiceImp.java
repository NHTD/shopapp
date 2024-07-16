package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.CartItemRequest;
import com.example.shopapp.dtos.request.OrderCreationRequest;
import com.example.shopapp.dtos.request.OrderUpdateRequest;
import com.example.shopapp.dtos.response.OrderResponse;
import com.example.shopapp.enums.OrderStatusEnum;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.OrderMapper;
import com.example.shopapp.models.*;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImp implements OrderService {

    UserRepository userRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;

    @Override
    public OrderResponse createOrder(OrderCreationRequest request, OrderStatusEnum orderStatusEnum) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("User with id {} is not found", request.getUserId()));

        Order order = orderMapper.orderToOrder(request);

        order.setUser(user);
        order.setOrderDate(new Date());
        if(OrderStatusEnum.PENDING.equals(orderStatusEnum)){
            order.setStatus(OrderStatusEnum.PENDING);
        }

        Date shippingDate = request.getShippingDate()==null ? new Date() : request.getShippingDate();
        if(shippingDate==null || shippingDate.before(new Date())){
            throw new ShopAppModelsNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalMoney(request.getTotalMoney());

        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for(CartItemRequest cartItemRequest : request.getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Long productId = cartItemRequest.getProductId();
            int quantity = cartItemRequest.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ShopAppModelsNotFoundException("Product not found with id {} ", productId));

            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            orderDetail.setPrice(product.getPrice());

            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        order.setOrderDetails(orderDetails);

        return orderMapper.orderToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("User with id {} not found", id));

        return orderMapper.orderToOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long id, OrderUpdateRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Order with id {} not found", id));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("User with id {} not found", id));

        orderMapper.orderToUpdateOrder(order, request);
        order.setUser(user);

        return orderMapper.orderToOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()){
            throw new ShopAppModelsNotFoundException("User with id {} is not found", id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(orderMapper :: orderToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public Page<OrderResponse> getOrdersByKeyword(String keyword, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByKeyword(keyword, pageable);
        return orderPage.map(orderMapper::orderToOrderResponse);
    }
}
