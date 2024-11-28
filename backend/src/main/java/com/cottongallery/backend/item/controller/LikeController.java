package com.cottongallery.backend.item.controller;

import com.cottongallery.backend.common.argumentResolver.annotation.Login;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.item.service.command.LikeCommandService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;

    @PostMapping("/{itemId}")
    public ResponseEntity<Response<?>> addLike(@Login AccountSessionDTO accountSessionDTO, @PathVariable Long itemId) {
        likeCommandService.createLike(accountSessionDTO, itemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "좋아요를 추가하였습니다."), HttpStatus.CREATED);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Response<?>> unLike(@Login AccountSessionDTO accountSessionDTO, @PathVariable Long itemId) {
        likeCommandService.deleteLike(accountSessionDTO, itemId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "좋아요를 취소하였습니다."), HttpStatus.OK);
    }
}
