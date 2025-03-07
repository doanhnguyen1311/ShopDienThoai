package com.doanh.ShopDienThoai.Exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class AppException extends RuntimeException {
    private final int code;
    private final String message;

    // Constructor cho AppException
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // Gọi constructor của RuntimeException
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
