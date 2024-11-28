package com.cottongallery.backend.auth.controller.api;

import com.cottongallery.backend.auth.dto.request.AuthRequest;
import com.cottongallery.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "계정 관리 - 인증 및 권한", description = "인증/인가 관련 API 및 테스트를 제공합니다.")
public interface AuthApi {

    @Operation(summary = "로그인",
            description = "사용자 로그인을 처리하고 Access Token 및 Refresh Token을 쿠키에 설정합니다.")
    ResponseEntity<Response<?>> login(@RequestBody AuthRequest authRequest, HttpServletResponse response);

    @Operation(summary = "로그아웃",
            description = "사용자의 Access Token 및 Refresh Token을 삭제하고 로그아웃 처리합니다.")
    ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response);

    @Operation(summary = "토큰 갱신",
            description = "Refresh Token을 사용하여 새로운 Access Token을 생성하고 쿠키에 설정합니다.")
    ResponseEntity<Response<?>> refreshToken(HttpServletRequest request, HttpServletResponse response);

}
