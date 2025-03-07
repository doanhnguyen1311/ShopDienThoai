package com.doanh.ShopDienThoai.Exception;

public enum ErrorCode {
  USER_NOT_FOUND(1, "User not found"),
  INVALID_INPUT(2, "Invalid input provided"),
  UNAUTHORIZED_ACCESS(3, "Unauthorized access"),
  USER_NOT_LOGIN(4, "You need login!");

  private final int code;
  private final String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
