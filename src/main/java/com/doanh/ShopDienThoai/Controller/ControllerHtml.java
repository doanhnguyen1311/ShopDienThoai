package com.doanh.ShopDienThoai.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHtml {

  @GetMapping("/gioithieu")
  public String gioiThieu() {
    return "gioithieu";
  }
}
