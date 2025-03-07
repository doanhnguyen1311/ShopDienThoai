package com.doanh.ShopDienThoai.Mapper;

import com.doanh.ShopDienThoai.dto.request.PermissionRequest;
import com.doanh.ShopDienThoai.dto.respone.PermissionRespone;
import com.doanh.ShopDienThoai.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  Permission toPermission(PermissionRequest request);

  PermissionRespone toPermissionRespone(Permission permission);
}
