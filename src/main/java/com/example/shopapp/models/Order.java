package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends AbstractModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column(name = "email", length = 100)
    String email;

    @Column(name = "phone_number", nullable = false, length = 100)
    String phoneNumber;

    @Column(name = "address", length = 100)
    String address;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "status")
    String status;

    @Column(name = "total_money")
    Integer totalMoney;

    @Column(name = "shipping_method")
    String shippingMethod;

    @Column(name = "shipping_address")
    String shippingAddress;

    @Column(name = "shipping_date")
    Date shippingDate;

    @Column(name = "tracking_number")
    String trackingNumber;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "payment_status")
    String paymentStatus;

    @Column(name = "payment_date")
    String paymentDate;

    @Column(name = "active")
    boolean active;

}
