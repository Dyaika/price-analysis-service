package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Панель админимтратора.", description = "Авторизация для изменения таблиц.")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/getCookie")
    public String getCookie(@CookieValue(value = "test", defaultValue = "defaultValue") String testCookie) {
        return "Value of test cookie: " + testCookie;
    }

    @PostMapping("/setCookie")
    public void setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("test", "testValue");
        response.addCookie(cookie);
    }

    @GetMapping("/deleteCookie")
    public void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("test", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
