package com.doanh.ShopDienThoai.security;

import com.doanh.ShopDienThoai.entity.KhachHang;
import com.doanh.ShopDienThoai.entity.Role;
import com.doanh.ShopDienThoai.enums.Roles;
import com.doanh.ShopDienThoai.repository.KhachHangRepository;
import com.doanh.ShopDienThoai.repository.RoleRepository;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationInitConfig {

  @Autowired private PasswordEncoder passwordEncoder;

  @Bean
  ApplicationRunner applicationRunner(
      KhachHangRepository khachHangRepository, RoleRepository roleRepository) {
    return args -> {
      if (khachHangRepository.findByUsername("admin") == null) {
        var roles = new HashSet<Role>();
        Role adminRoles = roleRepository.findByName(Roles.ADMIN.name());
        roles.add(adminRoles);
        KhachHang khachHang =
            KhachHang.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .roles(roles)
                .build();
        khachHangRepository.save(khachHang);
        log.warn("admin tạo bởi pass: 123, xin hãy đổi lại để đảm bảo an toàn!");
      }
    };
  }
}
