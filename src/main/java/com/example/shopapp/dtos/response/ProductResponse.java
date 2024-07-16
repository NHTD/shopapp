package com.example.shopapp.dtos.response;

import com.example.shopapp.models.Category;
import com.example.shopapp.models.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends AbstractAuditingModelResponse{
    Long id;
    String name;
    Float price;
    String thumbnail;
    String description;
    Category category;
    @JsonProperty("product_images")
    List<ProductImage> productImages;
}
