package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.security.JwtTokenProvider;
import me.dyaika.marketplace.security.RoleChecker;
import me.dyaika.marketplace.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Панель администратора.", description = "Авторизация для изменения таблиц.")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;
    private final RoleChecker roleChecker;

    public AdminController(JwtTokenProvider jwtTokenProvider, AdminService adminService, RoleChecker roleChecker) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminService = adminService;
        this.roleChecker = roleChecker;
    }

    @ApiOperation("Вход администратора.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        if (adminService.authenticate(username, password) != null) {
            String token = jwtTokenProvider.generateToken(username, "ADMIN"); // Здесь укажите реальную роль пользователя
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok("Login successful");
        } else {
            Cookie cookie = new Cookie("token", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.badRequest().body("Login failed");
        }
    }

    @ApiOperation("Выход администратора.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Удаляем куки
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("Logout successful");
    }

    @ApiOperation("Регистрация нового администратора.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        if (roleChecker.checkRole("ADMIN", request)){
            adminService.saveAdmin(username, password);
            return ResponseEntity.ok("Register successful");
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @ApiOperation("Проверка роли в куки.")
    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(@CookieValue(value = "token", defaultValue = "") String token) {
        if (token.isEmpty()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String username = jwtTokenProvider.getUsernameFromToken(token);
        if (username != null && jwtTokenProvider.validateToken(token)) {
            // Токен валиден, возвращаем информацию о пользователе
            return ResponseEntity.ok("Authenticated as " + username);
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @ApiOperation("Удаление администратора.")
    @DeleteMapping("/remove-admin")
    public ResponseEntity<String> removeAdmin(@RequestParam String username, HttpServletRequest httpRequest){
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            adminService.removeAdmin(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
