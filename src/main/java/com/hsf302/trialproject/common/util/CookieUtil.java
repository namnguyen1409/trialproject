package com.hsf302.trialproject.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CookieUtil {


    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public String getCookie(HttpServletRequest request, String cookieName) {
        var cookies = request.getCookies();
        if(cookies != null) {
            for(var cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String getCookie(String cookieName) {
        var cookies = request.getCookies();
        if(cookies != null) {
            for(var cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    public void deleteCookie(String cookieName, String path, Boolean httpOnly, Boolean secure) {
        var cookies = request.getCookies();
        if(cookies != null) {
            for(var cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    cookie.setMaxAge(0);
                    cookie.setPath(path);
                    cookie.setHttpOnly(httpOnly);
                    cookie.setSecure(secure);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public void addCookie(String cookieName, String cookieValue, int maxAge, String path, Boolean httpOnly, Boolean secure) {
        var cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        response.addCookie(cookie);
    }

}
