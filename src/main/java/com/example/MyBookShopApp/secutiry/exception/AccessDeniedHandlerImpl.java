package com.example.MyBookShopApp.secutiry.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AccessDeniedException exception
    ) throws IOException, ServletException {
        httpServletResponse.sendRedirect("/signin");
    }
}
