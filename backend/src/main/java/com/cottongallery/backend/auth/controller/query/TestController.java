package com.cottongallery.backend.auth.controller.query;

import com.cottongallery.backend.auth.controller.query.api.AuthTestApi;
import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController implements AuthTestApi {
  @Override
  @GetMapping("/user/test")
  public String userTest(@Login AccountSessionDTO accountSessionDTO) {
    log.debug("argumentResolver Test : {}", accountSessionDTO.getUsername());
    // Logic to verify USER role
    return "User test endpoint accessed";
  }

  @Override
  @GetMapping("/admin/test")
  public String adminTest() {
    // Logic to verify ADMIN role
    return "Admin test endpoint accessed";
  }

  @Override
  @GetMapping("/public/test")
  public String publicTest() {
    return "Public test endpoint accessed";
  }

}
