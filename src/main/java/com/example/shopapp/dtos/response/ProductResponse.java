package com.example.shopapp.dtos.response;

import com.example.shopapp.models.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
}
