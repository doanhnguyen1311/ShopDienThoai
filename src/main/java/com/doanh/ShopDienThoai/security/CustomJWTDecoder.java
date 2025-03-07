package com.doanh.ShopDienThoai.security;

import com.doanh.ShopDienThoai.dto.request.IntrospectRequest;
import com.doanh.ShopDienThoai.service.AuthenticationService;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomJWTDecoder implements JwtDecoder {
  @Value("${jwt.signerKey}")
  private String signerKey;

  @Autowired private AuthenticationService authenticationService;

  private NimbusJwtDecoder nimbusJwtDecoder = null;

  @Override
  public Jwt decode(String token) throws JwtException {
    try {
      var response =
          authenticationService.introspect(IntrospectRequest.builder().token(token).build());
      log.error("Kiem tra token: {}", response);
      if (!response.isValid()) {
        throw new JwtException("token Invalid");
      }
    } catch (JwtException e) {
      throw new JwtException("loi: " + e.getMessage(), e);
    }

    if (Objects.isNull(nimbusJwtDecoder)) {
      SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
      nimbusJwtDecoder =
          NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }
    return nimbusJwtDecoder.decode(token);
  }
}
