package com.example.MyBookShopApp.aspectAOP;

import com.example.MyBookShopApp.annotations.UserActionToCartLoggable;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * AOP логирование действий пользователя с книгами
 *
 * @author Иван Стрельцов
 */
@Aspect
@Component
@Slf4j
public class LoggerUserActionAspect {

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.annotations.UserActionToPostponedLoggable)")
    public void loggingUserActionToPostponedPointcut() {
    }

    @AfterReturning(value = " args(slug, user) && @annotation(userActionToCartLoggable)")
    public void beforeLoggingUserActionToCartAdvice(JoinPoint joinPoint, String slug, BookstoreUserDetails user,
                                                    UserActionToCartLoggable userActionToCartLoggable) {
        if (joinPoint.getSignature().getName().equals("handleChangeBookStatus")) {
            log.info("Пользователь: " + user.getBookstoreUser().getEmail() + " положил в корзину книгу с идентификатором " + slug);
        } else {
            log.info("Пользователь: " + user.getBookstoreUser().getEmail() + " удалил из корзины книгу с идентификатором " + slug);
        }
    }

    @AfterReturning(value = " args(slug, user) && loggingUserActionToPostponedPointcut()", argNames = "joinPoint,slug,user")
    public void beforeLoggingUserActionToPostponedAdvice(JoinPoint joinPoint, String slug, BookstoreUserDetails user) {
        if (joinPoint.getSignature().getName().equals("handleChangeBookStatus")) {
            log.info("Пользователь: " + user.getBookstoreUser().getEmail() + " предпочел отложить книгу с идентификатором " + slug);
        } else {
            log.info("Пользователь: " + user.getBookstoreUser().getEmail() + " удалил из отложенного книгу с идентификатором " + slug);
        }
    }
}
