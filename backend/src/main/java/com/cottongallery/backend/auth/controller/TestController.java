package com.cottongallery.backend.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class TestController {
  @GetMapping("/user/test")
  public String userTest() {
    // Logic to verify USER role
    return "User test endpoint accessed";
  }

  @GetMapping("/admin/test")
  public String adminTest() {
    // Logic to verify ADMIN role
    return "Admin test endpoint accessed";
  }

}
