package com.doanh.ShopDienThoai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class InvalidatedToken {

  @Id private String id;
  private Date expiryTime;
}
