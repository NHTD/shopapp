package com.example.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "social_accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount extends AbstractModel{

    @Column(name = "provider", nullable = false, length = 20)
    String provider;

    @Column(name = "provider_id", nullable = false, length = 20)
    String providerId;

    @Column(name = "email", length = 150)
    String email;

}
