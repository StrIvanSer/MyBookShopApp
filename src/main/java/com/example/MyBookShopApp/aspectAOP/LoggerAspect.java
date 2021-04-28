package com.example.MyBookShopApp.aspectAOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * AOP логирование вызова методов контроллеров API
 *
 * @author Иван Стрельцов
 */
@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.annotations.APIDurationLoggable)")
    public void loggingAPIPointcut() {
    }

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.annotations.APIDurationLoggable)")
    public void loggingMethodPointcut() {
    }

    /**
     * Логирование API методов, для обнаружения проблемных запросов
     */
    @Around(value = "loggingAPIPointcut()")
    public Object aroundDurationAPITrackingAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;
        long durationMils = new Date().getTime();
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("При вызове API метода: " + proceedingJoinPoint.getSignature().getName() + " произошла ошибка" + throwable.getMessage());
        }

        durationMils = new Date().getTime() - durationMils;
        if (durationMils > 5000L) {
            log.warn("Вызов API метода: " + proceedingJoinPoint.getSignature().getName() + " превысил 5000 mills !");
        }

        return returnValue;
    }

    /**
     * Логирование специфичных и "больших" методов, для обнаружения проблемных запросов в БД
     */
    @Around(value = "loggingMethodPointcut()")
    public Object aroundDurationTrackingAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        long durationMils = new Date().getTime();
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("При вызове метода: " + proceedingJoinPoint.getSignature().getName() + " произошла ошибка" + throwable.getMessage());
        }

        durationMils = new Date().getTime() - durationMils;
        if (durationMils > 1000L) {
            log.warn("При вызове метода: " + proceedingJoinPoint.getSignature().getName() + " запрос в БД превысил 1000 mills !");
        }

        return returnValue;
    }


    @Before(value = "execution(* com.example.MyBookShopApp.data.ResourceStorage.saveNewBookImage(..))")
    public void beforeResourceStorageSaveNewBookImage() {
        log.info("Началась попытка добавления нового изображения");
    }

    @After(value = "execution(* com.example.MyBookShopApp.data.ResourceStorage.saveNewBookImage(..))")
    public void afterResourceStorageSaveNewBookImage(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName()).append("! ");
        Arrays.stream(joinPoint.getArgs()).forEach(arg -> message.append("args: ").append(arg).append("! "));
        message.append(" файл успешно загружен");
        log.info(message.toString());
    }

}
