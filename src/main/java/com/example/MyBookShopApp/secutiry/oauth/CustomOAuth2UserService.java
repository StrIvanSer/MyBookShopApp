package com.example.MyBookShopApp.secutiry.oauth;

import com.example.MyBookShopApp.data.book.BookstoreUser;
import com.example.MyBookShopApp.repo.BookstoreUserRepository;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BookstoreUserDetails loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);
        String email = (String)oAuth2User.getAttributes().get("email");
        BookstoreUser bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(email);
        if(nonNull(bookstoreUser)){
            return new BookstoreUserDetails(bookstoreUser);
        }
        bookstoreUser = new BookstoreUser();
        bookstoreUser.setEmail(email);
        bookstoreUser.setIsOAuth2(true);
        bookstoreUser.setIdOAuth((String) oAuth2User.getAttributes().get("id"));
        bookstoreUser.setPassword(passwordEncoder.encode((CharSequence) oAuth2User.getAttributes().get("id")));
        bookstoreUser.setName((String) oAuth2User.getAttributes().get("name"));
        bookstoreUserRepository.save(bookstoreUser);
        return new BookstoreUserDetails(bookstoreUser);

    }

}
