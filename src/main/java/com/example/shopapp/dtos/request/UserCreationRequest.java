package com.example.shopapp.dtos.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String name;
    String fullName;
    String phoneNumber;
    String password;
    boolean active;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
    Set<String> roles;
}
