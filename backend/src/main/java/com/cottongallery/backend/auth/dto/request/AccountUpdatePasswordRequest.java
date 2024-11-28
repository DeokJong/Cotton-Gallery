package com.cottongallery.backend.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountUpdatePasswordRequest {
    @NotBlank
    @Size(min = 8, max=15)
    @Schema(description = "비밀번호 (8자 이상 15자 이하)")
    private String password;

    @NotBlank
    @Size(min = 8, max=15)
    @Schema(description = "비밀번호 확인 (8자 이상 15자 이하)")
    private String confirmPassword;
}
