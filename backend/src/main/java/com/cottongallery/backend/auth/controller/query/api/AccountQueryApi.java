package com.cottongallery.backend.auth.controller.query.api;

import com.cottongallery.backend.auth.dto.account.response.AccountCheckUsernameResponse;
import com.cottongallery.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "계정 관리 - 조회", description = "계정 조회와 관련된 API")
public interface AccountQueryApi {

    @Operation(summary = "사용자명 중복 체크", description = "주어진 사용자명(username)이 이미 사용 중인지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자명 중복 체크 성공")
    })
    ResponseEntity<Response<AccountCheckUsernameResponse>> checkUsername(@Parameter(description = "중복 체크할 사용자명") @RequestParam("username") String username);

}

