package com.example.shopapp.dtos.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String name;
    String fullName;
    String phoneNumber;
    String password;
    boolean active;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
    Set<RoleResponse> roles;
}
