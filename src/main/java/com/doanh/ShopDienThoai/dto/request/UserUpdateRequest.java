package com.doanh.ShopDienThoai.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
  private String maKhachHang;
  private String tenKhachHang;
  private int tuoi;
  private String diaChi;
  private String soDienThoai;
  private String password;
  List<String> roles;
}
