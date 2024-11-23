package com.cottongallery.backend.auth.dto.account.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Schema(description = "사용자 이름 중복 여부 응답 데이터")
public class AccountCheckUsernameResponse {

    @Schema(description = "사용자 이름 중복 여부 (true: 중복됨, false: 중복되지 않음)")
    private Boolean isDuplicated;
}
