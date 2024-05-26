package com.example.shopapp.dtos.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String name;
    String fullName;
    String phoneNumber;
    String password;
    boolean active;
    Date dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
    List<String> roles;
}
