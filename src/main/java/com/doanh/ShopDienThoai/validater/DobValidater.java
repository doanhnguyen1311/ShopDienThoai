package com.doanh.ShopDienThoai.validater;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DobValidater implements ConstraintValidator<DobConstraint, Integer> {

  private int min;

  @Override
  public void initialize(DobConstraint constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    min = constraintAnnotation.min();
  }

  @Override
  public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
    return integer >= min;
  }
}
