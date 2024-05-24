package com.example.shopapp.exception;

import com.example.shopapp.utils.FormatUtils;

public abstract class ShopAppException extends RuntimeException{
    public ShopAppException(String message) {
        super(message);
    }
    public ShopAppException(String message, Object ...arguments) {
        super(formatMessage(message, arguments));
    }

    public ShopAppException(String message, Throwable cause, Object ...arguments) {
        super(formatMessage(message, arguments), cause);
    }

    public ShopAppException(Throwable cause) {
        super(cause);
    }

    public static String formatMessage(String message, Object ...arguments){
        return FormatUtils.format(message, arguments);
    }
}
