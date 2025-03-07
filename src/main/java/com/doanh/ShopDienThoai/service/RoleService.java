package com.doanh.ShopDienThoai.service;

import com.doanh.ShopDienThoai.Mapper.RoleMapper;
import com.doanh.ShopDienThoai.dto.request.RoleRequest;
import com.doanh.ShopDienThoai.dto.respone.RoleRespone;
import com.doanh.ShopDienThoai.repository.PermissionRepository;
import com.doanh.ShopDienThoai.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleService {

  PermissionRepository permissionRepository;
  RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  public RoleRespone create(RoleRequest request) {
    var roles = roleMapper.toRole(request);
    var permissions = permissionRepository.findAllById(request.getPermissions());
    roles.setPermissions(new HashSet<>(permissions));
    roles = roleRepository.save(roles);
    return roleMapper.toRoleRespone(roles);
  }

  public List<RoleRespone> getAll() {
    return roleRepository.findAll().stream().map(roleMapper::toRoleRespone).toList();
  }

  public void delete(String role) {
    roleRepository.deleteById(role);
  }
}
