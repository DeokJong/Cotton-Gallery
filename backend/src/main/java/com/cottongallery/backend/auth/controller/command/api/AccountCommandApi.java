package com.cottongallery.backend.auth.controller.command.api;

import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.auth.dto.account.request.AccountUpdateEmailRequest;
import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "계정 관리 - 생성 및 변경", description = "계정 생성 및 변경과 관련된 API")
public interface AccountCommandApi {

    @Operation(summary = "회원 가입", description = "사용자 계정을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    ResponseEntity<Response<?>> addAccount(@Validated @RequestBody AccountCreateRequest accountCreateRequest,
                                                  BindingResult bindingResult);

    @Operation(summary = "이메일 변경", description = "사용자 이메일 정보를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    ResponseEntity<Response<?>> editAccountEmail(@Login AccountSessionDTO accountSessionDTO,
                                                        @Validated @RequestBody AccountUpdateEmailRequest accountUpdateEmailRequest,
                                                        BindingResult bindingResult);
}
