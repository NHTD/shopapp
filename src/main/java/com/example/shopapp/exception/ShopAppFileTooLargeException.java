package com.example.shopapp.exception;

public class ShopAppFileTooLargeException extends ShopAppRuntimeException{
    public ShopAppFileTooLargeException(String message) {
        super(message);
    }

    public ShopAppFileTooLargeException(String message, Object... arguments) {
        super(message, arguments);
    }

    public ShopAppFileTooLargeException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public ShopAppFileTooLargeException(Throwable cause) {
        super(cause);
    }
}
