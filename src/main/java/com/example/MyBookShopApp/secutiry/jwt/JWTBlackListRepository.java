package com.example.MyBookShopApp.secutiry.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JWTBlackListRepository extends JpaRepository<JWTBlackList, Long> {

    @Query(value = "DELETE FROM public.token WHERE created_at < date_trunc('week', now())- INTERVAL '7days' ",
            nativeQuery = true)
    void deleteOldTokens();

    @Query(value = "SELECT * FROM public.token WHERE created_at < date_trunc('week', now())- INTERVAL '7days' ",
            nativeQuery = true)
    List<JWTBlackList> getOldTokens();

    JWTBlackList findJwtBlacklistByToken(String token);
}