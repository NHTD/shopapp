package com.example.shopapp.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopAppErrorDetail {
    String field;
    String message;
}
