package com.example.shopapp.dtos.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    String fullName;
    String email;
    String phoneNumber;
    String address;
    LocalDateTime orderDate;
    String status;
    Integer totalMoney;
    String shippingMethod;
    String shippingAddress;
    Date shippingDate;
    String trackingNumber;
    String paymentMethod;
    String paymentStatus;
    String paymentDate;
    boolean active;
    Long userId;
}
