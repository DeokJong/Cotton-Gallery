package com.cottongallery.backend.auth.service.query;

public interface AccountQueryService {
    Boolean isUsernameDuplicate(String username);
    String getNameByUsername(String username);
}
