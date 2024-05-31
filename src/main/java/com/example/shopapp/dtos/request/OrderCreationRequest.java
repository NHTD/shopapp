package com.example.shopapp.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {
    @JsonProperty("full_name")
    String fullName;
    String email;
    @JsonProperty("phone_number")
    String phoneNumber;
    String address;
    LocalDateTime orderDate;
    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    Float totalMoney;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("shipping_date")
    Date shippingDate;
    String trackingNumber;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("user_id")
    Long userId;
}
