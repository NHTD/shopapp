package com.example.shopapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail extends AbstractModel{

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    Float price;

    @Column(name = "number_of_products")
    int numberOfProducts;

    @Column(name = "total_money", nullable = false)
    int totalMoney;

    @Column(name = "color")
    String color;
}
