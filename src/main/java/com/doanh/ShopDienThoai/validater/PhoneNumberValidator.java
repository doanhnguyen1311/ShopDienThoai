package com.doanh.ShopDienThoai.validater;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
  @Override
  public void initialize(PhoneNumberConstraint constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String sdt, ConstraintValidatorContext constraintValidatorContext) {
    String regex = "^0[3|7|8|9]\\d{8}$";
    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(sdt);

    return matcher.matches();
  }
}
