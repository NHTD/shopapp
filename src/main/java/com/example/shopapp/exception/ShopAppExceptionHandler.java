package com.example.shopapp.exception;

import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.utils.ShopAppRestApiResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopAppExceptionHandler {

    @ExceptionHandler(ShopAppModelsNotFoundException.class)
    ResponseEntity<ShopAppErrorStatus> handleShopAppModelsNotFoundException(ShopAppModelsNotFoundException exception){
        return buildShopAppServerErrorResponse(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(ShopAppFileTooLargeException.class)
    ResponseEntity<ShopAppErrorStatus> handleShopAppFileTooLargeException(ShopAppFileTooLargeException exception){
        return buildShopAppServerErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE, exception);
    }

    @ExceptionHandler(ShopAppFileUnSupportedMediaTypeException.class)
    ResponseEntity<ShopAppErrorStatus> handleShopAppFileUnSupportedMediaTypeException(ShopAppFileUnSupportedMediaTypeException exception){
        return buildShopAppServerErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception);
    }

    private <E extends Throwable> ResponseEntity<ShopAppErrorStatus> buildShopAppServerErrorResponse(
            HttpStatus httpStatus,
            E exception
    ) {
        ShopAppErrorStatus errorStatus;
        errorStatus = ShopAppRestApiResponseUtils.buildServerResponseException(httpStatus, exception.getMessage(), null);

        return new ResponseEntity<>(errorStatus, httpStatus);
    }
}
