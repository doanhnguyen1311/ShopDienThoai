package com.doanh.ShopDienThoai.repository;

import com.doanh.ShopDienThoai.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
  @Query("SELECT k FROM KhachHang k WHERE k.username = :username AND k.password = :password")
  public KhachHang login(@Param("username") String username, @Param("password") String password);

  KhachHang findByUsername(String username);

  boolean existsByUsername(String username);

  //    KhachHang update(KhachHang user);
}
