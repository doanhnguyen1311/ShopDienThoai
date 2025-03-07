package com.doanh.ShopDienThoai.entityDTO;

import com.doanh.ShopDienThoai.entity.Role;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class KhachHangDTO {
  private Long maKhachHang;
  private String tenKhachHang;
  private int tuoi;
  private Long maLoaiKhachHang;
  private String diaChi;
  private String soDienThoai;
  private String username;
  private String password;
  private Set<Role> roles;
}
