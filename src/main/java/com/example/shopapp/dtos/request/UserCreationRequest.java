package com.example.shopapp.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String name;
    @JsonProperty("full_name")
    String fullName;
    @JsonProperty("phone_number")
    String phoneNumber;
    @Size(min = 6, message = "Password must have at least {min} characters")
    String password;
    @JsonProperty("retype_password")
    String retypePassword;
    String address;
    boolean active;
    @JsonProperty("date_of_birth")
    LocalDate dateOfBirth;
    int facebookAccountId;
    int googleAccountId;
    Set<String> roles;
}
