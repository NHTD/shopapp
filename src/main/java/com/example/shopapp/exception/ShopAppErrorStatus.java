package com.example.shopapp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopAppErrorStatus extends AbstractShopAppErrorStatus{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<ShopAppErrorDetail> errorDetails;
}
