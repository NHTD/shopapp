package com.example.shopapp.dtos.response;

import com.example.shopapp.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Product product;
    Float price;
    @JsonProperty("number_of_products")
    int numberOfProducts;
    @JsonProperty("total_money")
    int totalMoney;
    String color;
    @JsonProperty("order_id")
    Long orderId;
}
