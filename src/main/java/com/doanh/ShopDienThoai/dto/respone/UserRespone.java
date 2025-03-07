package com.doanh.ShopDienThoai.dto.respone;

import com.doanh.ShopDienThoai.entity.Role;
import java.util.Set;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserRespone {
  private Long maKhachHang;
  private String tenKhachHang;
  private int tuoi;
  private String diaChi;
  private String soDienThoai;
  private String username;
  private String password;
  private int maLoaiKhachHang;

  private Set<Role> roles;
}
