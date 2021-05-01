package com.example.MyBookShopApp.aspectAOP;

import com.example.MyBookShopApp.annotations.APIDurationLoggable;
import com.example.MyBookShopApp.annotations.MethodDurationLoggable;
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
    @Around(value = "@annotation(durationLoggable)")
    public Object aroundDurationAPITrackingAdvice(ProceedingJoinPoint proceedingJoinPoint, APIDurationLoggable durationLoggable) {
        Object returnValue = null;
        long durationMils = new Date().getTime();
        String nameClass = !durationLoggable.className().equals("") ? " в классе " + durationLoggable.className() : "";
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(String.format("При вызове %s метода: %s %s произошла ошибка %s", durationLoggable.nameDescription(),
                    proceedingJoinPoint.getSignature().getName(), nameClass, throwable.getMessage()));
        }
        durationMils = new Date().getTime() - durationMils;
        if (durationMils >= durationLoggable.timeThreshold()) {
            log.warn(String.format("Вызов %s метода: %s%s превысил %d mills !", durationLoggable.nameDescription(),
                    proceedingJoinPoint.getSignature().getName(), nameClass, durationLoggable.timeThreshold()));
        }
        return returnValue;
    }

    /**
     * Логирование специфичных и "больших" методов, для обнаружения проблемных запросов в БД
     */
    @Around(value = "@annotation(methodDurationLoggable)")
    public Object aroundDurationTrackingAdvice(ProceedingJoinPoint proceedingJoinPoint, MethodDurationLoggable methodDurationLoggable) {
        long durationMils = new Date().getTime();
        Object returnValue = null;
        String nameClass = !methodDurationLoggable.className().equals("") ? " в классе " + methodDurationLoggable.className() : "";
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(String.format("При вызове %s метода: %s%s произошла ошибка%s", methodDurationLoggable.nameDescription(),
                    proceedingJoinPoint.getSignature().getName(), nameClass, throwable.getMessage()));
        }

        durationMils = new Date().getTime() - durationMils;
        if (durationMils >= methodDurationLoggable.timeThreshold()) {
            log.warn(String.format("При вызове %s метода: %s%s запрос в БД превысил %d mills !",
                    methodDurationLoggable.nameDescription(), proceedingJoinPoint.getSignature().getName(),
                    nameClass, methodDurationLoggable.timeThreshold()));
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
