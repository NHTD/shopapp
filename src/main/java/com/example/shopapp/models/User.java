package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractAuditingModel{

    @Column(name = "name", length = 350, nullable = false)
    String name;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column(name = "phone_number", length = 11, nullable = false)
    String phoneNumber;

    @Column(name = "address", length = 200, nullable = false)
    String password;

    boolean active;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "facebook_account_id")
    int facebookAccountId;

    @Column(name = "google_account_id")
    int googleAccountId;

    @ManyToMany
    Set<Role> roles;
}
