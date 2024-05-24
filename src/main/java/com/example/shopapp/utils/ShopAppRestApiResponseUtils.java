package com.example.shopapp.utils;

import com.example.shopapp.exception.ShopAppErrorDetail;
import com.example.shopapp.exception.ShopAppErrorStatus;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ShopAppRestApiResponseUtils {


    public static ShopAppErrorStatus buildServerResponseException(
            HttpStatus httpStatus, String message, List<ShopAppErrorDetail> errorDetails)
    {
        ShopAppErrorStatus shopAppErrorStatus = new ShopAppErrorStatus();
        shopAppErrorStatus.setHttpStatus(httpStatus);
        shopAppErrorStatus.setCreatedAt(new Date());
        shopAppErrorStatus.setMessage(message);
        if(errorDetails!=null){
            shopAppErrorStatus.setErrorDetails(errorDetails);
        }

        return shopAppErrorStatus;
    }
}
