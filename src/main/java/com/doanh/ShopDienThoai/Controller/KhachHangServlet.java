package com.doanh.ShopDienThoai.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KhachHangServlet {
  @GetMapping("/login")
  public String showLogin() {
    return "login";
  }

  @GetMapping("/users/add")
  public String addUsers() {
    return "themKhachHang";
  }

  @GetMapping("/addUsersSuccess")
  public String addUsersSuccess() {
    return "addUsersSuccess";
  }
}
