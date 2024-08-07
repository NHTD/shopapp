package com.example.shopapp.models;

import com.example.shopapp.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    Date orderDate;

    @Column(name = "status")
    OrderStatusEnum status;

    @Column(name = "total_money")
    Float totalMoney;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<OrderDetail> orderDetails;
}
