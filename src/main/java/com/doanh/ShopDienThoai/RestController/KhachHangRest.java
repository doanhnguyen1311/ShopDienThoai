package com.doanh.ShopDienThoai.RestController;

import com.doanh.ShopDienThoai.dto.request.ApiRespone;
import com.doanh.ShopDienThoai.dto.request.UserCreatingRequest;
import com.doanh.ShopDienThoai.dto.request.UserUpdateRequest;
import com.doanh.ShopDienThoai.dto.respone.UserRespone;
import com.doanh.ShopDienThoai.entity.KhachHang;
import com.doanh.ShopDienThoai.service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class KhachHangRest {
  @Autowired
  private KhachHangService khachHangService;

  @GetMapping
  public ResponseEntity<?> getKhachHang() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Username : {}", authentication.getName());
    authentication
        .getAuthorities()
        .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority())); // lay roles
    return ResponseEntity.ok(khachHangService.getKhachHang());
  }

  // Khách hàng dto

  @GetMapping("/khachHangDto")
  public ResponseEntity<?> getKhachHangDTO() {
    return ResponseEntity.ok(khachHangService.getKhachHangDto());
  }

  @GetMapping("/xemKhachHang")
  public KhachHang xemKH(HttpServletRequest req) {
    HttpSession ss = req.getSession();
    KhachHang kh = (KhachHang) ss.getAttribute("login");
    return kh;
  }

  // them khach hang moi
  @PostMapping("/add")
  public ApiRespone<?> themKH(@Valid @RequestBody UserCreatingRequest khachHang) {
    return ApiRespone.<UserRespone>builder()
        .result(khachHangService.themKhachHang(khachHang))
        .build();
  }

  @GetMapping("/myInfo")
  public ApiRespone<KhachHang> getMyInfo() {
    return ApiRespone.<KhachHang>builder().result(khachHangService.getMyInfo()).build();
  }

  // tim khach hang theo id
  @GetMapping("/{maKH}")
  public ResponseEntity<?> getKhachHangById(@PathVariable Long maKH) {
    return ResponseEntity.ok(khachHangService.getKhachHangById(maKH));
  }

  // delete Khách hàng
  @DeleteMapping("/delete/{maKhachHang}")
  public ResponseEntity<?> deleteKhachHang(@PathVariable Long maKhachHang) {
    return ResponseEntity.ok(khachHangService.deleteKhachHangById(maKhachHang));
  }

  // update Khach Hang

  @PutMapping("/update/{id}")
  public ApiRespone<?> updateKhachHang(@PathVariable Long id, @RequestBody UserUpdateRequest kh) {
    return ApiRespone.<UserRespone>builder()
        .result(khachHangService.updateKhachHang(id, kh))
        .build();
  }
}
