package com.example.shopapp.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractShopAppErrorStatus {

    @JsonIgnore
    private HttpStatus httpStatus;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Date createdAt;

    @JsonProperty("status")
    public int getStatus(){
        return this.httpStatus.value();
    }

    @JsonProperty("error")
    public String getError(){
        return this.httpStatus.getReasonPhrase();
    }

}
