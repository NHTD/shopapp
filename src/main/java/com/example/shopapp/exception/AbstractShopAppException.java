package com.example.shopapp.exception;

import lombok.Getter;
import lombok.Setter;

public class AbstractShopAppException extends ShopAppRuntimeException{
    @Getter @Setter AbstractShopAppErrorStatus errorStatus;

    public AbstractShopAppException(String message, Object... arguments) {
        super(message, arguments);
    }

    public AbstractShopAppException(String message, Throwable cause, Object... arguments) {
        super(message, cause, arguments);
    }

    public AbstractShopAppException(Throwable cause) {
        super(cause);
    }
}
