package com.doanh.ShopDienThoai.dto.request;

import com.doanh.ShopDienThoai.entity.Role;
import com.doanh.ShopDienThoai.validater.DobConstraint;
import com.doanh.ShopDienThoai.validater.PhoneNumberConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatingRequest {
  @Size(min = 3, message = "Username Invalid")
  private String username;

  @Size(min = 3, message = "Name Invalid")
  private String tenKhachHang;

  @DobConstraint(min = 18)
  private int tuoi;

  @Size(min = 3, message = "Address Invalid")
  private String diaChi;

  @PhoneNumberConstraint
  @NotNull(message = "So dien thoai khong duoc de trong")
  private String soDienThoai;

  @Size(min = 8, message = "Invalid Password")
  private String password;

  private Set<Role> roles;
}
