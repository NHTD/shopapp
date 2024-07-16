package com.example.shopapp.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String name;
    @JsonProperty("full_name")
    String fullName;
    @JsonProperty("phone_number")
    String phoneNumber;
    String address;
    @JsonProperty("is_active")
    boolean active;
    @JsonProperty("date_of_birth")
    LocalDate dateOfBirth;
    @JsonProperty("facebook_account_id")
    int facebookAccountId;
    @JsonProperty("google_account_id")
    int googleAccountId;
    Set<RoleResponse> roles;
}
