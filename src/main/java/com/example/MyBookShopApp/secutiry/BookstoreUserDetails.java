package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class BookstoreUserDetails implements UserDetailsI, OAuth2User {

    private final BookstoreUser bookstoreUser;

    public BookstoreUserDetails(BookstoreUser bookstoreUser) {
        this.bookstoreUser = bookstoreUser;
    }

    public BookstoreUser getBookstoreUser() {
        return bookstoreUser;
    }

    @Override
    public <A> A getAttribute(String name) {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return bookstoreUser.getPassword();
    }

    @Override
    public String getUsername() {
        return bookstoreUser.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getEmail() {
        return bookstoreUser.getEmail();
    }

    @Override
    public String getName() {
        return null;
    }
}
