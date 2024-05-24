package com.example.shopapp.exception;

public class ShopAppModelsNotFoundException extends ShopAppRuntimeException{
    public ShopAppModelsNotFoundException(String message) {
        super(message);
    }

    public ShopAppModelsNotFoundException(String message, Object... arguments) {
        super(message, arguments);
    }

    public ShopAppModelsNotFoundException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public ShopAppModelsNotFoundException(Throwable cause) {
        super(cause);
    }
}
