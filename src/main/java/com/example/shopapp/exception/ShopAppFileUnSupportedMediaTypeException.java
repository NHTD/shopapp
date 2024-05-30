package com.example.shopapp.exception;

public class ShopAppFileUnSupportedMediaTypeException extends ShopAppRuntimeException{
    public ShopAppFileUnSupportedMediaTypeException(String message) {
        super(message);
    }

    public ShopAppFileUnSupportedMediaTypeException(String message, Object... arguments) {
        super(message, arguments);
    }

    public ShopAppFileUnSupportedMediaTypeException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public ShopAppFileUnSupportedMediaTypeException(Throwable cause) {
        super(cause);
    }
}
