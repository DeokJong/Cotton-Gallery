package com.cottongallery.backend.auth.controller;

import com.cottongallery.backend.auth.controller.api.AddressCommandApi;
import com.cottongallery.backend.auth.dto.request.AddressCreateRequest;
import com.cottongallery.backend.auth.service.AddressCommandService;
import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@Slf4j
@RestController
@RequestMapping("/api/user/address")
@RequiredArgsConstructor
public class AddressCommandController implements AddressCommandApi {

    private final AddressCommandService addressCommandService;

    @Override
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> addAddress(@Login AccountSessionDTO accountSessionDTO,
                                                  @Validated @RequestBody AddressCreateRequest addressCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("주소 생성 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        addressCommandService.createAddress(addressCreateRequest, accountSessionDTO);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "주소 생성에 성공했습니다."), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping(value = "/{addressId}/primary", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> editPrimaryAddress(@Login AccountSessionDTO accountSessionDTO,
                                                          @PathVariable Long addressId) {

        addressCommandService.updatePrimaryAddressById(addressId, accountSessionDTO);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "PRIMARY 주소가 성공적으로 변경되었습니다."), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{addressId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> removeAddress(@Login AccountSessionDTO accountSessionDTO,
                                                     @PathVariable Long addressId) {

        addressCommandService.deleteAddress(addressId, accountSessionDTO);
        
        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "주소가 성공적으로 삭제되었습니다."), HttpStatus.OK);
    }
}
