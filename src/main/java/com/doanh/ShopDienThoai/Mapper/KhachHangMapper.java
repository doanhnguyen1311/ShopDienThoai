package com.doanh.ShopDienThoai.Mapper;

import com.doanh.ShopDienThoai.dto.request.UserCreatingRequest;
import com.doanh.ShopDienThoai.dto.request.UserRequest;
import com.doanh.ShopDienThoai.dto.request.UserUpdateRequest;
import com.doanh.ShopDienThoai.dto.respone.UserRespone;
import com.doanh.ShopDienThoai.entity.KhachHang;
import com.doanh.ShopDienThoai.entityDTO.KhachHangDTO;
import com.doanh.ShopDienThoai.entityDTO.LoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {

  KhachHang toKhachHang(KhachHangDTO dto);

  KhachHangDTO toKhachHangDto(KhachHang kh);

  LoginDTO toLoginDTO(KhachHang kh);

  KhachHang toUser(UserRequest request);

  UserRespone toUserRespone(KhachHang kh);

  @Mapping(target = "roles", ignore = true)
  void createUser(@MappingTarget KhachHang kh, UserCreatingRequest request);

  @Mapping(target = "roles", ignore = true)
  void updateUser(@MappingTarget KhachHang kh, UserUpdateRequest request);
}
