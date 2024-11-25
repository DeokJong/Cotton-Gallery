package com.cottongallery.backend.auth.controller.query.api;

import com.cottongallery.backend.auth.dto.address.response.AddressListResponse;
import com.cottongallery.backend.auth.dto.address.response.AddressResponse;
import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "주소 관리", description = "주소 조회와 관련된 API")
public interface AddressQueryApi {

    @Operation(summary = "주소 리스트 조회", description = "로그인된 사용자의 주소 리스트를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주소 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "주소 리스트 조회에 필요한 리소스를 찾을 수 없습니다.")
    })
    ResponseEntity<Response<AddressListResponse>> retrieveAddressList(@Login AccountSessionDTO accountSessionDTO);

    @Operation(summary = "기본 주소 조회", description = "로그인된 사용자의 기본 설정 주소(PRIMARY)를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 주소 조회 성공"),
            @ApiResponse(responseCode = "404", description = "기본 주소 조회에 필요한 리소스를 찾을 수 없습니다.")
    })
    ResponseEntity<Response<AddressResponse>> retrievePrimaryAddress(@Login AccountSessionDTO accountSessionDTO);
}
