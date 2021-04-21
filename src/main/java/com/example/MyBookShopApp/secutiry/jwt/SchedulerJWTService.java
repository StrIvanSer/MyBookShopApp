package com.example.MyBookShopApp.secutiry.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SchedulerJWTService {

    private static final String CRON = "00 00 * * 1 *";

    private final JWTBlackListServiceImpl jwtBlackListService;

    @Autowired
    public SchedulerJWTService(JWTBlackListServiceImpl jwtBlackListService) {
        this.jwtBlackListService = jwtBlackListService;
    }

    @Scheduled(cron = CRON)
    public void deleteOldTokens() {
        List<JWTBlackList> jwtBlackList = jwtBlackListService.getOldTokens();
       if(!jwtBlackList.isEmpty()){
           jwtBlackListService.deleteOldToken();
           log.info(String.format("Из BlackListToken было удалено: %d записей", jwtBlackList.size()));
       }
    }

}
