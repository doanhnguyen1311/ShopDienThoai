package com.doanh.ShopDienThoai.repository;

import com.doanh.ShopDienThoai.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Role findByName(String role);
}
