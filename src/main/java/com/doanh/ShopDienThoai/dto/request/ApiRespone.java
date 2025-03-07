package com.doanh.ShopDienThoai.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiRespone<T> {
  private int code = 1000;
  private String message;
  private T result;
}
