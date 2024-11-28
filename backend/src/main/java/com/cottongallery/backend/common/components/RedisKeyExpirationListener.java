package com.cottongallery.backend.common.components;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.cottongallery.backend.auth.service.impl.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeyExpirationListener implements MessageListener {

    private final AdminService adminService;

    @Override
    public void onMessage(@NonNull Message message, @Nullable byte[] pattern) {
        String expiredKey = message.toString();

        // 만료된 키가 admin 계정인지 확인
        if (expiredKey.startsWith("admin:")) {
            String username = expiredKey.split(":")[1];
            log.info("Redis key expired: {}", username);

            // 새 admin 계정 생성
            adminService.deleteAdminAccount(username);
            adminService.initAdminAccount();
        }
    }
}

