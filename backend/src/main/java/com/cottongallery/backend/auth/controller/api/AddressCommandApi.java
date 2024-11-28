package com.cottongallery.backend.auth.controller.api;

import com.cottongallery.backend.auth.dto.request.AddressCreateRequest;
import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "주소 관리", description = "주소 조회와 관련된 API")
public interface AddressCommandApi {
    @Operation(summary = "주소 추가", description = "새로운 주소를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "주소 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터로 인해 주소 추가 실패"),
            @ApiResponse(responseCode = "404", description = "주소 추가에 필요한 리소스를 찾을 수 없습니다.")
    })
    ResponseEntity<Response<?>> addAddress(@Login AccountSessionDTO accountSessionDTO,
                                           @Validated @RequestBody AddressCreateRequest addressCreateRequest, BindingResult bindingResult);

    @Operation(summary = "기본 주소 변경", description = "지정된 주소를 기본 주소로 설정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "기본 주소 변경 성공"),
            @ApiResponse(responseCode = "404", description = "기본 주소 변경에 필요한 리소스를 찾을 수 없습니다.")
    })
    ResponseEntity<Response<?>> editPrimaryAddress(@Login AccountSessionDTO accountSessionDTO,
                                                   @Parameter(description = "기본 주소로 설정할 주소 ID") @PathVariable Long addressId);

    @Operation(summary = "주소 삭제", description = "지정된 주소를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주소 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "주소 삭제에 필요한 리소스를 찾을 수 없습니다.")
    })
    ResponseEntity<Response<?>> removeAddress(@Login AccountSessionDTO accountSessionDTO,
                                              @Parameter(description = "삭제할 주소 ID") @PathVariable Long addressId);
}
