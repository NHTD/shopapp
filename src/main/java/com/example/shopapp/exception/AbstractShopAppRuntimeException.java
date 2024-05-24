package com.example.shopapp.exception;

public class AbstractShopAppRuntimeException extends AbstractShopAppException{
    public AbstractShopAppRuntimeException(String message, Object... arguments) {
        super(message, arguments);
    }

    public AbstractShopAppRuntimeException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public AbstractShopAppRuntimeException(Throwable cause) {
        super(cause);
    }
}
