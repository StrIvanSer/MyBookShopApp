package com.example.MyBookShopApp.secutiry.jwt;


import java.util.List;

public interface JWTBlackListService {

    JWTBlackList getByToken(String token);

    JWTBlackList saveToken(JWTBlackList jwtBlacklist);

    void deleteOldToken();

    List<JWTBlackList> getOldTokens();

}
