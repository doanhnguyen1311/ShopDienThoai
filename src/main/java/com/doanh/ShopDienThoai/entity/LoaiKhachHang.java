package com.doanh.ShopDienThoai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LoaiKhachHang {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long loaiKhachHang;

  private String tenLoaiKhachHang;
}
