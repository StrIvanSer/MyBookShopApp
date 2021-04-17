package com.example.MyBookShopApp.secutiry.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTBlackListRepository extends JpaRepository<JWTBlackList,Long> {

    JWTBlackList findJwtBlacklistByToken(String token);
}