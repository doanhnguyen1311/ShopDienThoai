package com.doanh.ShopDienThoai.validater;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD}) // Annotation này có thể áp dụng cho các field (thuộc tính).
@Retention(RUNTIME) // Annotation này sẽ được xử lý trong runtime.
@Constraint(
    validatedBy = {
      PhoneNumberValidator.class
    }) // Đánh dấu đây là một ràng buộc (constraint), và sẽ được xử lý bởi lớp xác thực. Ở đây chưa
// có lớp xác thực nào được chỉ định.
public @interface PhoneNumberConstraint {

  String message() default "So dien thoai khong hop le";

  Class<?>[]
      groups() default {}; // Nhóm của constraint này, thường dùng trong trường hợp muốn phân loại

  // các constraint.

  Class<? extends Payload>[]
      payload() default {}; // Cho phép gửi các thông tin bổ sung cho constraint, ví dụ như các
  // metadata liên quan.
}
