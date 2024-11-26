package com.cottongallery.backend.common.components;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisInitializer {

    private final RedisConnectionFactory redisConnectionFactory;

    @PostConstruct
    public void enableKeyspaceNotifications() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            // Redis CONFIG SET notify-keyspace-events Ex
            byte[] set = "SET".getBytes();
            byte[] key = "notify-keyspace-events".getBytes();
            byte[] value = "Ex".getBytes();

            connection.execute("CONFIG", set, key, value);
        }
    }
}

