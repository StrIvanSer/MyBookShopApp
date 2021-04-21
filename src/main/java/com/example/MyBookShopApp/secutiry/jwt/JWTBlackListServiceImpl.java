package com.example.MyBookShopApp.secutiry.jwt;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JWTBlackListServiceImpl implements JWTBlackListService {

    private final JWTBlackListRepository jwtBlackListRepository;

    public JWTBlackListServiceImpl(JWTBlackListRepository jwtBlackListRepository) {
        this.jwtBlackListRepository = jwtBlackListRepository;
    }

    @Override
    public JWTBlackList getByToken(String token) {
        return this.jwtBlackListRepository.findJwtBlacklistByToken(token);
    }

    @Override
    public JWTBlackList saveToken(JWTBlackList jwtBlacklist) {
        return this.jwtBlackListRepository.save(jwtBlacklist);
    }

    @Override
    public void deleteOldToken() {
        this.jwtBlackListRepository.deleteOldTokens();
    }

    @Override
    public List<JWTBlackList> getOldTokens() {
        return this.jwtBlackListRepository.getOldTokens();
    }
}
