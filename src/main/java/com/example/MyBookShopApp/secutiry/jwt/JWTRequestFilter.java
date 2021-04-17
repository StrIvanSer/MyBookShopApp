package com.example.MyBookShopApp.secutiry.jwt;

import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetailsService;
import com.example.MyBookShopApp.secutiry.exception.JwtAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final JWTBlackListService jwtBlackListService;

    public JWTRequestFilter(BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil,
                            JWTBlackListService jwtBlackListService) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.jwtBlackListService = jwtBlackListService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        String token = null;
        String username = null;
        Cookie tokenCookie = null;
        Cookie[] cookies = httpServletRequest.getCookies();

        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        tokenCookie = cookie;
                        token = cookie.getValue();
                        username = jwtUtil.extractUsername(token);
                        break;
                    }
                }
                if (nonNull(token) && !token.isEmpty() && nonNull(this.jwtBlackListService.getByToken(token))) {
                    throw new JwtAuthenticationException("JWT token is expired or invalid");
                } else if (nonNull(username) && isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    try {
                        BookstoreUserDetails userDetails = (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(username);
                        if (jwtUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());

                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        } else {
                            throw new JwtAuthenticationException("JWT token is expired or invalid");
                        }
                    } catch (UsernameNotFoundException e) {
                        tokenCookie.setMaxAge(0);
                        tokenCookie.setValue(null);
                        httpServletResponse.addCookie(tokenCookie);
                        SecurityContextHolder.clearContext();
                        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
                        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, SecurityContextHolder.getContext().getAuthentication());
                    }
                }

            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
