package com.doanh.ShopDienThoai.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<AppException> handleAppException(AppException exception) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception);
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity
            .badRequest()
            .body(exception.getMessage());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException exception) {
    return ResponseEntity
            .badRequest()
            .body(exception.getMessage());
  }
}
