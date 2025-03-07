package com.doanh.ShopDienThoai.service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

import com.doanh.ShopDienThoai.dto.request.AuthenticationRequest;
import com.doanh.ShopDienThoai.dto.request.IntrospectRequest;
import com.doanh.ShopDienThoai.dto.request.LogoutRequest;
import com.doanh.ShopDienThoai.dto.request.RefreshRequest;
import com.doanh.ShopDienThoai.dto.respone.AuthenticationRespone;
import com.doanh.ShopDienThoai.dto.respone.IntrospectRespone;
import com.doanh.ShopDienThoai.entity.InvalidatedToken;
import com.doanh.ShopDienThoai.entity.KhachHang;
import com.doanh.ShopDienThoai.repository.InvalidatedRepository;
import com.doanh.ShopDienThoai.repository.KhachHangRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthenticationService {

  @NonFinal // chỉ định không phải final nên không cần generate lombok
  @Value("${jwt.signerKey}")
  private String SIGNER_KEY;

  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURATION;

  @NonFinal
  @Value("${jwt.refreshable-duration}")
  protected long REFRESHABLE_DURATION;

  InvalidatedRepository invalidatedRepository;
  KhachHangRepository khachHangRepository;

  public IntrospectRespone introspect(IntrospectRequest request) throws RuntimeException {
    log.info("ok");
    var token = request.getToken();
    boolean isValid = true;
    try {
      verifyToken(token, false);
    } catch (RuntimeException | JOSEException | ParseException e) {
      isValid = false;
    }
    return IntrospectRespone.builder().valid(isValid).build();
  }

  public void logout(LogoutRequest request) throws Exception {
    try {
      var signToken = verifyToken(request.getToken(), true);
      String jit = signToken.getJWTClaimsSet().getJWTID();
      Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

      InvalidatedToken invalidatedToken =
          InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

      invalidatedRepository.save(invalidatedToken);
    } catch (RuntimeException e) {
      log.info("Token đã hết hạn!");
    }
  }

  public AuthenticationRespone authenticated(AuthenticationRequest request) {
    var user = khachHangRepository.findByUsername(request.getUsername());
    if (user == null) {
      throw new RuntimeException("Lỗi authentication!");
    }
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
    var token = generateToken(user);
    try {
      return AuthenticationRespone.builder().token(token).authenticated(true).build();
    } catch (RuntimeException e) {
      throw new RuntimeException("Loi: " + e);
    }
  }

  private SignedJWT verifyToken(String token, boolean isRefresh)
      throws RuntimeException, JOSEException, ParseException {
    JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
    SignedJWT signedJWT = SignedJWT.parse(token);

    Date expiryTime =
        (isRefresh)
            ? new Date(
                signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESHABLE_DURATION, ChronoUnit.HOURS)
                    .toEpochMilli())
            : signedJWT.getJWTClaimsSet().getExpirationTime();

    var verified = signedJWT.verify(jwsVerifier);

    if (!(verified && expiryTime.after(new Date()))) {
      throw new RuntimeException("Loi authentication!");
    }

    if (invalidatedRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
      throw new RuntimeException("Loi authenticateion!");

    return signedJWT;
  }

  public AuthenticationRespone refreshToken(RefreshRequest request)
      throws ParseException, JOSEException {
    var signJWT = verifyToken(request.getToken(), true);

    var jit = signJWT.getJWTClaimsSet().getJWTID();

    var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
    InvalidatedToken invalidatedToken =
        InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

    invalidatedRepository.save(invalidatedToken);

    var username = signJWT.getJWTClaimsSet().getSubject();

    var user = khachHangRepository.findByUsername(username);

    if (user == null) {
      throw new RuntimeException("Khong tim thay khach hang phu hop!");
    }
    var token = generateToken(user);
    return AuthenticationRespone.builder().token(token).authenticated(true).build();
  }

  public String generateToken(KhachHang kh) {
    JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet jwtClaimsSet =
        new JWTClaimsSet.Builder()
            .subject(kh.getUsername()) // Chủ thể của token (người dùng)
            .issuer("Doanh") // Người phát hành token (tên miền hoặc tổ chức)
            .issueTime(new Date()) // Thời gian phát hành
            .expirationTime(
                new Date(
                    Instant.now()
                        .plus(VALID_DURATION, ChronoUnit.HOURS)
                        .toEpochMilli())) // Thời gian hết hạn (1 giờ)
            .jwtID(UUID.randomUUID().toString())
            .claim("scope", buildScope(kh)) // Thêm custom claim
            .build();
    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    JWSObject jwsObject =
        new JWSObject(jwsHeader, payload); // Cái này phải có jws header và payload
    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      log.error("Không thể tạo token: " + e);
      throw new RuntimeException(e);
    }
  }

  private String buildScope(KhachHang khachHang) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    if (!CollectionUtils.isEmpty(
        khachHang
            .getRoles())) { // kiểm tra xem khachHang.getRoles() có phải là danh sách rỗng không
      khachHang
          .getRoles()
          .forEach(
              role -> {
                stringJoiner.add(role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                  role.getPermissions()
                      .forEach(permission -> stringJoiner.add(permission.getName()));
                }
              }); // nếu không thì thêm vào StringJoiner
    }
    return stringJoiner.toString();
  }
}
