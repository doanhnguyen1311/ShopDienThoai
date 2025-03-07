package com.doanh.ShopDienThoai.dto.request;

import java.util.Set;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoleRequest {
  private String name;
  private String description;
  Set<String> permissions;
}
