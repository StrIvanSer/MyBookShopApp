package com.example.MyBookShopApp.secutiry.jwt;


public interface JWTBlackListService {

    JWTBlackList getByToken(String token);

    JWTBlackList saveToken(JWTBlackList jwtBlacklist);

}
