package com.doanh.ShopDienThoai.RestController;

import com.doanh.ShopDienThoai.dto.request.ApiRespone;
import com.doanh.ShopDienThoai.dto.request.RoleRequest;
import com.doanh.ShopDienThoai.dto.respone.RoleRespone;
import com.doanh.ShopDienThoai.service.RoleService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRest {
  RoleService roleService;

  @PostMapping
  public ApiRespone<RoleRespone> create(@RequestBody RoleRequest request) {
    return ApiRespone.<RoleRespone>builder().result(roleService.create(request)).build();
  }

  @GetMapping("")
  public ApiRespone<List<RoleRespone>> getAll() {
    return ApiRespone.<List<RoleRespone>>builder().result(roleService.getAll()).build();
  }

  @DeleteMapping("/{permission}")
  public ApiRespone<Void> delete(@PathVariable("permission") String permission) {
    roleService.delete(permission);
    return ApiRespone.<Void>builder().build();
  }
}
