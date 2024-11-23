package com.cottongallery.backend.auth.dto.account.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 요청 데이터")
public class AccountCreateRequest {

    @NotBlank
    @Schema(description = "사용자의 이름")
    private String name;

    @NotBlank
    @Schema(description = "사용자 이름 (로그인 ID)")
    private String username;

    @NotBlank
    @Size(min = 8, max=15)
    @Schema(description = "비밀번호 (8자 이상 15자 이하)")
    private String password;

    @NotBlank
    @Size(min = 8, max=15)
    @Schema(description = "비밀번호 확인 (8자 이상 15자 이하)")
    private String confirmPassword;

    @Email
    @NotBlank
    @Schema(description = "사용자의 이메일 주소")
    private String email;

    @NotBlank
    @Schema(description = "휴대폰 번호")
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 5)
    @Schema(description = "우편번호 (5자리)")
    private String zipcode;

    @NotBlank
    @Schema(description = "도로명 주소")
    private String street;

    @NotBlank
    @Schema(description = "상세 주소")
    private String detail;
}
