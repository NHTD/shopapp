package com.example.shopapp.exception;

public class ShopAppNullPointerException extends ShopAppRuntimeException{
    public ShopAppNullPointerException(String message) {
        super(message);
    }

    public ShopAppNullPointerException(String message, Object... arguments) {
        super(message, arguments);
    }

    public ShopAppNullPointerException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public ShopAppNullPointerException(Throwable cause) {
        super(cause);
    }
}
