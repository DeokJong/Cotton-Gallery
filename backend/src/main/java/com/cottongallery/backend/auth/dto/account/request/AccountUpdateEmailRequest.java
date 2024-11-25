package com.cottongallery.backend.auth.dto.account.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountUpdateEmailRequest {
    @Email
    @NotBlank
    @Schema(description = "사용자의 이메일 주소")
    private String email;
}
