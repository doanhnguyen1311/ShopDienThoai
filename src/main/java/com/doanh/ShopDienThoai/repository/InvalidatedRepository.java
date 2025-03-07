package com.doanh.ShopDienThoai.repository;

import com.doanh.ShopDienThoai.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedRepository extends JpaRepository<InvalidatedToken, String> {}
