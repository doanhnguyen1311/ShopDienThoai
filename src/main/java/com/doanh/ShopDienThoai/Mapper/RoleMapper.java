package com.doanh.ShopDienThoai.Mapper;

import com.doanh.ShopDienThoai.dto.request.RoleRequest;
import com.doanh.ShopDienThoai.dto.respone.RoleRespone;
import com.doanh.ShopDienThoai.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  @Mapping(target = "permissions", ignore = true)
  Role toRole(RoleRequest roleRequest);

  RoleRespone toRoleRespone(Role role);
}
