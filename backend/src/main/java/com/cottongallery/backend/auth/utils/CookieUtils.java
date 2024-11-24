package com.cottongallery.backend.auth.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {
    public Cookie createCookie(String name, String value, long maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (maxAge / 1000));
        return cookie;
    }

    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie deleteCookie = new Cookie(name, null);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);
    }
}
