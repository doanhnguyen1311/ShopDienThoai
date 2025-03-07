package com.doanh.ShopDienThoai.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Role {
  @Id private String name;
  private String description;

  @ManyToMany // mối quan hệ nhiều nhiều
  Set<Permission> permissions;
}
