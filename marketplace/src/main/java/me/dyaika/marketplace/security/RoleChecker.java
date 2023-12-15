package me.dyaika.marketplace.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class RoleChecker {
    private final JwtTokenProvider jwtTokenProvider;

    public RoleChecker(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean checkRole(String role, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    String tokenRole = jwtTokenProvider.getRoleFromToken(token);
                    return Objects.equals(tokenRole, role);
                }
            }
        }

        return false;
    }

}
