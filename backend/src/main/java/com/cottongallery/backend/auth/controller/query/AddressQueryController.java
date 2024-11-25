package com.cottongallery.backend.auth.controller.query;

import com.cottongallery.backend.auth.controller.query.api.AddressQueryApi;
import com.cottongallery.backend.auth.dto.address.response.AddressListResponse;
import com.cottongallery.backend.auth.dto.address.response.AddressResponse;
import com.cottongallery.backend.auth.service.query.AddressQueryService;
import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;


@Slf4j
@RestController
@RequestMapping("/api/user/address")
@RequiredArgsConstructor
public class AddressQueryController implements AddressQueryApi {

    private final AddressQueryService addressQueryService;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<AddressListResponse>> retrieveAddressList(@Login AccountSessionDTO accountSessionDTO) {

        AddressListResponse addressListResponse = addressQueryService.getAddressListResponseByAccountUsername(accountSessionDTO.getUsername());

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "주소 리스트 조회에 성공했습니다.", addressListResponse), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/primary", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<AddressResponse>> retrievePrimaryAddress(@Login AccountSessionDTO accountSessionDTO) {

        AddressResponse primaryAddress = addressQueryService.getPrimaryAddressResponseByUsername(accountSessionDTO.getUsername());

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "기본 주소를 조회했습니다.", primaryAddress), HttpStatus.OK);
    }
}
