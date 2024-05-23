package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token extends AbstractModel{

    @Column(name = "token", length = 255)
    String token;

    @Column(name = "token_type", length = 50)
    String tokenType;

    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

    boolean revoked;
    boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
