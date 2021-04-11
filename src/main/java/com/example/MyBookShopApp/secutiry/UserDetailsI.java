package com.example.MyBookShopApp.secutiry;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface UserDetailsI extends UserDetails {

    @Override
    Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    String getPassword();

    @Override
    String getUsername();

    @Override
    boolean isAccountNonExpired();

    @Override
    boolean isAccountNonLocked();

    @Override
    boolean isCredentialsNonExpired();

    @Override
    boolean isEnabled();

    String getEmail();
}
