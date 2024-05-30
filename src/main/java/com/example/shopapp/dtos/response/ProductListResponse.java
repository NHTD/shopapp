package com.example.shopapp.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListResponse {
    List<ProductResponse> products;
    int totalPages;
}
