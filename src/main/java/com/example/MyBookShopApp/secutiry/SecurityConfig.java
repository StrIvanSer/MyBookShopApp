package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.secutiry.exception.AccessDeniedHandlerImpl;
import com.example.MyBookShopApp.secutiry.jwt.CustomLogoutHandler;
import com.example.MyBookShopApp.secutiry.jwt.JWTBlackListService;
import com.example.MyBookShopApp.secutiry.jwt.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTRequestFilter filter;
    private final JWTBlackListService jwtBlackListService;

    private static final String SIGN_IN = "/signin";
    private static final String SIGN = "/index";

    //
    @Autowired
    public SecurityConfig(BookstoreUserDetailsService bookstoreUserDetailsService, JWTRequestFilter filter,
                          JWTBlackListService jwtBlackListService) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.filter = filter;
        this.jwtBlackListService = jwtBlackListService;
    }

    //
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(bookstoreUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/my/*", "/profile", "/books/changeBookStatus/**", "/books/postponed", "/books/cart", "/recently_views").authenticated()//.hasRole("USER")
                .antMatchers("/books/img/save/**").authenticated()
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage(SIGN_IN)
                .failureUrl(SIGN_IN)//страница логина
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler(new CustomLogoutHandler(jwtBlackListService))
                .logoutSuccessUrl(SIGN_IN).deleteCookies("token")
                .and().oauth2Login()
                .and().oauth2Client();

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());
    }


}