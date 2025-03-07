package com.doanh.ShopDienThoai.RestController;

import com.doanh.ShopDienThoai.dto.request.UserCreatingRequest;
import com.doanh.ShopDienThoai.dto.respone.UserRespone;
import com.doanh.ShopDienThoai.service.KhachHangService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc // tao mot mock request
public class KhachHangRestTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private KhachHangService khachHangService;

  private UserCreatingRequest userCreatingRequest;
  private UserRespone userRespone;

  @BeforeEach
  void initData() {
    userCreatingRequest =
        UserCreatingRequest.builder()
            .username("join")
            .tenKhachHang("Nguyen Van Ha")
            .diaChi("Hue")
            .soDienThoai("0938488392")
            .tuoi(20)
            .password("12345678")
            .build();

    userRespone =
        UserRespone.builder()
            .username("join")
            .tenKhachHang("Nguyen Van Ha")
            .diaChi("Hue")
            .soDienThoai("0938488392")
            .tuoi(20)
            .build();
  }

  @Test
  void createUser() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    String content = objectMapper.writeValueAsString(userCreatingRequest);
    // WHEN
    mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(content));
    // THEN
  }
}
