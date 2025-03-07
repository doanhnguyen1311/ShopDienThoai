package com.doanh.ShopDienThoai.RestController;

import com.doanh.ShopDienThoai.dto.request.*;
import com.doanh.ShopDienThoai.dto.respone.AuthenticationRespone;
import com.doanh.ShopDienThoai.dto.respone.IntrospectRespone;
import com.doanh.ShopDienThoai.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationRest {

  AuthenticationService authenticationService;

  @PostMapping("/token")
  ApiRespone<AuthenticationRespone> authenticate(@RequestBody AuthenticationRequest request) {
    var result = authenticationService.authenticated(request);
    return ApiRespone.<AuthenticationRespone>builder().result(result).build();
  }

  @PostMapping("/introspect")
  ApiRespone<IntrospectRespone> introspect(@RequestBody IntrospectRequest request)
      throws Exception {
    var result = authenticationService.introspect(request);
    return ApiRespone.<IntrospectRespone>builder().result(result).build();
  }

  @PostMapping("/logout")
  ApiRespone<Void> logout(@RequestBody LogoutRequest request) throws Exception {
    authenticationService.logout(request);
    return ApiRespone.<Void>builder().build();
  }

  @PostMapping("/refresh")
  ApiRespone<AuthenticationRespone> refreshToken(@RequestBody RefreshRequest request)
      throws ParseException, JOSEException {
    var result = authenticationService.refreshToken(request);
    return ApiRespone.<AuthenticationRespone>builder().result(result).build();
  }
}
