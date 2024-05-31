package com.example.shopapp.enums;

import com.example.shopapp.exception.ShopAppNullPointerException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum OrderStatusEnum {
    PAID,
    PENDING;

    public static OrderStatusEnum fromString(String stringStatus){
        return Arrays.stream(OrderStatusEnum.values())
                .filter(val -> StringUtils.equalsIgnoreCase(val.name(), stringStatus))
                .findFirst()
                .orElseThrow(() -> new ShopAppNullPointerException("Unsupported OrderStatusEnum with status {}", stringStatus));
    }
}
