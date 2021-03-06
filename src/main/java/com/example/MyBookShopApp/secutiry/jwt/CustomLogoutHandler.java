package com.example.MyBookShopApp.secutiry.jwt;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@NoArgsConstructor
@Configuration
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private JWTBlackListService jwtBlackListService;

    @Autowired
    public CustomLogoutHandler(JWTBlackListService jwtBlackListService) {
        this.jwtBlackListService = jwtBlackListService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String token = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        if (nonNull(token) && isNull(jwtBlackListService.getByToken(token))) {
            JWTBlackList jwtBlacklist = new JWTBlackList();
            jwtBlacklist.setToken(token);
            jwtBlackListService.saveToken(jwtBlacklist);

        }
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, authentication);

        response.sendRedirect("/");

    }
}
