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
@Table(name = "khach_hang")
public class KhachHang {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ma_khach_hang")
  private Long maKhachHang;

  @Column(name = "ten_khach_hang")
  private String tenKhachHang;

  @Column(name = "tuoi")
  private int tuoi;

  @Column(name = "dia_chi")
  private String diaChi;

  @Column(name = "so_dien_thoai")
  private String soDienThoai;

  @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE SQL_Latin1_General_CP1_CI_AS")
// khong phan biet chu hoa thuong
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "ma_loai_khach_hang")
  private int maLoaiKhachHang;

  @ManyToMany @ToString.Exclude private Set<Role> roles;
}
