package com.cottongallery.backend.auth.controller.api;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "계정 관리 - 인증 및 권한", description = "인증/인가 관련 API 및 테스트를 제공합니다.")
public interface AuthTestApi {

    @Operation(
            summary = "유저 권한 테스트",
            description = "로그인된 유저의 권한을 확인합니다."
    )
    String userTest(@Login AccountSessionDTO accountSessionDTO);

    @Operation(
            summary = "관리자 권한 테스트",
            description = "로그인된 사용자가 관리자 권한을 보유했는지 확인합니다."
    )
    String adminTest();

    @Operation(
            summary = "공용 테스트",
            description = "인증 없이 접근 가능한 공용 테스트 API입니다."
    )
    String publicTest();
}
