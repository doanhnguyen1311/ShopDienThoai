package com.doanh.ShopDienThoai.dto.respone;

import java.util.Set;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoleRespone {
  private String name;
  private String description;
  Set<PermissionRespone> permissions;
}
