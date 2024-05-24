package com.example.shopapp.exception;

public class ShopAppRuntimeException extends ShopAppException{
    public ShopAppRuntimeException(String message) {
        super(message);
    }

    public ShopAppRuntimeException(String message, Object... arguments) {
        super(message, arguments);
    }

    public ShopAppRuntimeException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public ShopAppRuntimeException(Throwable cause) {
        super(cause);
    }
}
