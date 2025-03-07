package com.doanh.ShopDienThoai.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginDTO {
  private Long maKhachHang;
  private String tenKhachHang;
  private int tuoi;
  private Long maLoaiKhachHang;
  private String diaChi;
  private String soDienThoai;
  private String username;
}
