package com.doanh.ShopDienThoai.service;

import com.doanh.ShopDienThoai.Mapper.PermissionMapper;
import com.doanh.ShopDienThoai.dto.request.PermissionRequest;
import com.doanh.ShopDienThoai.dto.respone.PermissionRespone;
import com.doanh.ShopDienThoai.entity.Permission;
import com.doanh.ShopDienThoai.repository.PermissionRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionService {

  PermissionRepository permissionRepository;
  PermissionMapper permissionMapper;

  public PermissionRespone create(PermissionRequest request) {
    Permission permission = permissionMapper.toPermission(request);
    permission = permissionRepository.save(permission);
    return permissionMapper.toPermissionRespone(permission);
  }

  public List<PermissionRespone> getAll() {
    var permissions = permissionRepository.findAll();
    return permissions.stream().map(permissionMapper::toPermissionRespone).toList();
  }

  public void delete(String id) {
    permissionRepository.deleteById(id);
  }
}
