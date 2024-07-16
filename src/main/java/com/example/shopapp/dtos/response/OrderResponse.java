package com.example.shopapp.dtos.response;

import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    @JsonProperty("full_name")
    String fullName;
    String email;

    @JsonProperty("phone_number")
    String phoneNumber;

    String address;
    @JsonProperty("order_date")
    LocalDateTime orderDate;
    String status;
    @JsonProperty("total_money")
    Float totalMoney;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("shipping_date")
    Date shippingDate;
    @JsonProperty("tracking_number")
    String trackingNumber;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("payment_status")
    String paymentStatus;
    @JsonProperty("payment_date")
    String paymentDate;
    boolean active;
    User user;
    @JsonProperty("order_details")
    List<OrderDetail> orderDetails;
}
