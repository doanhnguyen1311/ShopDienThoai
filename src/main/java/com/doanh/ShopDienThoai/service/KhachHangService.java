package com.doanh.ShopDienThoai.service;

import com.doanh.ShopDienThoai.Exception.AppException;
import com.doanh.ShopDienThoai.Exception.ErrorCode;
import com.doanh.ShopDienThoai.Mapper.KhachHangMapper;
import com.doanh.ShopDienThoai.dto.request.UserCreatingRequest;
import com.doanh.ShopDienThoai.dto.request.UserUpdateRequest;
import com.doanh.ShopDienThoai.dto.respone.UserRespone;
import com.doanh.ShopDienThoai.entity.KhachHang;
import com.doanh.ShopDienThoai.entity.Role;
import com.doanh.ShopDienThoai.entityDTO.KhachHangDTO;
import com.doanh.ShopDienThoai.entityDTO.LoginDTO;
import com.doanh.ShopDienThoai.enums.Roles;
import com.doanh.ShopDienThoai.repository.KhachHangRepository;
import com.doanh.ShopDienThoai.repository.RoleRepository;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KhachHangService {
  @Autowired private KhachHangRepository khachHangRepository;

  @Autowired private KhachHangMapper mapper;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public List<KhachHangDTO> getKhachHangDto() {
    List<KhachHang> ds = khachHangRepository.findAll();
    List<KhachHangDTO> dsDTO = new ArrayList<>();
    for (KhachHang s : ds) {
      KhachHangDTO dto = mapper.toKhachHangDto(s);
      dsDTO.add(dto);
    }
    return dsDTO;
  }
  public LoginDTO Login(String username, String password) {
    KhachHang khachHang = khachHangRepository.login(username, password);
    return mapper.toLoginDTO(khachHang);
  }

  public UserRespone themKhachHang(UserCreatingRequest request) {
    boolean bool = khachHangRepository.existsByUsername(request.getUsername());
    KhachHang kh = new KhachHang();
    if (bool) {
      throw new RuntimeException("Username đã tồn tại!");
    }
    mapper.createUser(kh, request);
    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findByName(Roles.USER.name());
    roles.add(userRole);
    kh.setRoles(roles);
    kh.setPassword(passwordEncoder.encode(kh.getPassword())); // Mã hóa mật khẩu

    return mapper.toUserRespone(khachHangRepository.save(kh));
  }

  // mã hóa
  public String encode(String password) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    return passwordEncoder.encode(password);
  }

  // check mã hóasetPassword
  public boolean checkPass(String rawPass, String encodePass) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    return passwordEncoder.matches(rawPass, encodePass);
  }

  @PreAuthorize(
      "hasAnyAuthority(\"SCOPE_ADMIN\")") // kiểm tra coi phải là Admin hay không mới cho thực hiện
  // hàm
  public List<KhachHang> getKhachHang() {
    return khachHangRepository.findAll();
  }

  //    @PostAuthorize("hasAnyAuthority(\"SCOPE_ADMIN\")") // thực hiện hàm rồi mới kiểm tra quyền,
  // nhưng nếu quyền đúng là admin nó mới return kết quả
  @PostAuthorize("returnObject.username == authentication.name")
  // kiểm tra xem hàm này có trả về tên user=user đang đăng nhập không, nếu có thì cho return
  public KhachHang getKhachHangById(Long maKhachHang) {
    try {
      KhachHang kh =
              khachHangRepository
                      .findById(maKhachHang)
                      .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

      kh.setPassword(null);
      return kh;
    }catch (AppException e){
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
  }

  // hàm này để biết được user đang đăng nhập là ai
  // giống như là tạo một Khách Hàng và lưu session vào khách hàng vậy
  public KhachHang getMyInfo() {
    try {
      var context = SecurityContextHolder.getContext();
      String name = context.getAuthentication().getName();

      KhachHang kh = khachHangRepository.findByUsername(name);
      if (kh == null) {
        throw new AppException(ErrorCode.USER_NOT_LOGIN);
      }
      kh.setPassword(null);
      return kh;
    }
    catch (AppException e){
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
  }

  public String deleteKhachHangById(Long maKhachHang) {
    if (khachHangRepository.existsById(maKhachHang)) {
      khachHangRepository.deleteById(maKhachHang);
      return ResponseEntity.ok("Xóa Khách hàng thành công!").getBody();
    } else {
      throw new RuntimeException("Khách hàng cần xóa không tồn tại!");
    }
  }

  // update khachhang

  public UserRespone updateKhachHang(Long maKhachHang, UserUpdateRequest kh) {
    log.info(String.valueOf(kh));
    try {
      Optional<KhachHang> users = khachHangRepository.findById(maKhachHang);

      log.info("Sau khi tim: {} và mã khách hàng là {}", users, maKhachHang);
      KhachHang user = users.orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại!"));
      mapper.updateUser(user, kh);
      user.setPassword(passwordEncoder.encode(kh.getPassword()));
      var roles = roleRepository.findAllById(Collections.singleton(Roles.USER.name()));
      // Collections.singleton tạo một Set<> chứa 1 phần tử duy nhất
      user.setRoles(new HashSet<>(roles));
      log.info("Đã qua vòng!");
      log.info(String.valueOf(user.getMaKhachHang()));
      if (user.getMaKhachHang() == null) {
        log.info("Mã khách hàng null");
      }
      return mapper.toUserRespone(khachHangRepository.saveAndFlush(user));
    } catch (RuntimeException e) {
      throw new RuntimeException(e);
    }
  }
}
