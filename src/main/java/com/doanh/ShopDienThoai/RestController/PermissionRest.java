package com.doanh.ShopDienThoai.RestController;

import com.doanh.ShopDienThoai.dto.request.ApiRespone;
import com.doanh.ShopDienThoai.dto.request.PermissionRequest;
import com.doanh.ShopDienThoai.dto.respone.PermissionRespone;
import com.doanh.ShopDienThoai.service.PermissionService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionRest {
  PermissionService permissionService;

  @PostMapping
  public ApiRespone<PermissionRespone> create(@RequestBody PermissionRequest request) {
    return ApiRespone.<PermissionRespone>builder()
        .result(permissionService.create(request))
        .build();
  }

  @GetMapping("")
  public ApiRespone<List<PermissionRespone>> getAll() {
    return ApiRespone.<List<PermissionRespone>>builder().result(permissionService.getAll()).build();
  }

  @DeleteMapping("/{permission}")
  public ApiRespone<Void> delete(@PathVariable("permission") String permission) {
    permissionService.delete(permission);
    return ApiRespone.<Void>builder().build();
  }
}
