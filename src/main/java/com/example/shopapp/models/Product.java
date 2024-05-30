package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AbstractAuditingModel {
    @Column(name = "name", nullable = false, length = 350)
    String name;

    Float price;

    @Column(name = "thumbnail", nullable = true, length = 300)
    String thumbnail;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
