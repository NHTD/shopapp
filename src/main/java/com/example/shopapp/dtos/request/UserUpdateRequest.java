package com.example.shopapp.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String fullName;
    String phoneNumber;
    String password;
    @JsonProperty("retype_password")
    String retypePassword;
    String address;
    boolean active;
    LocalDate dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
}
