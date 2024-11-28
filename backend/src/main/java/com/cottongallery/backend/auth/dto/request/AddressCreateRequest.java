package com.cottongallery.backend.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressCreateRequest {
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
