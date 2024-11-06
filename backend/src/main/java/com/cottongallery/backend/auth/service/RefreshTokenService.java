package com.cottongallery.backend.auth.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {
    // TODO : DB 사용 
    private final ConcurrentHashMap<String, String> refreshTokenStore = new ConcurrentHashMap<>();

    public void saveRefreshToken(String username, String refreshToken) {
        refreshTokenStore.put(username, refreshToken);
    }

    public boolean isValidRefreshToken(String username, String refreshToken) {
        String storedToken = refreshTokenStore.get(username);
        return storedToken != null && storedToken.equals(refreshToken);
    }

    public void deleteRefreshToken(String username) {
        refreshTokenStore.remove(username);
    }
}

