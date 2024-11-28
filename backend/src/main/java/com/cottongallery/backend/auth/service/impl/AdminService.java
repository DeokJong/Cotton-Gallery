package com.cottongallery.backend.auth.service.impl;

import java.security.SecureRandom;
import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.common.constants.Role;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public void initAdminAccount() {
        // 1. 랜덤 값 생성
        String username = generateRandomString(20);
        String password = generateRandomString(20);
        String email = generateRandomString(8) + "@" + generateRandomString(8) + ".com";
        String phoneNumber = "000-0000-0000";

        // 2. Admin 계정 생성 및 저장
        Account admin = Account.createAccount("Admin", username, passwordEncoder.encode(password), email, phoneNumber, Role.ROLE_ADMIN);
        accountRepository.save(admin);

        // 3. Redis에 만료일 정보 저장 (예: 30일 후 만료)
        String redisKey = "admin:" + username + ":expiration";
        redisTemplate.opsForValue().set(redisKey, "active", Duration.ofHours(3));

        log.info("Admin account created with username: {}, password: {}, expiration: 3 hours", username, password);
    }

    public void deleteAdminAccount(String adminName) {
        // 1. Redis에서 admin 계정 삭제
        redisTemplate.delete("admin:" + adminName + ":expiration");

        // 2. DB에서 admin 계정 삭제
        accountRepository.deleteById(accountRepository.findByUsername(adminName).get().getId());

        log.info("Admin account deleted: {}", adminName);
    }

    private String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*?";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
