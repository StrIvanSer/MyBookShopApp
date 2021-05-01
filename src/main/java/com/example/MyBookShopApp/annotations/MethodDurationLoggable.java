package com.example.MyBookShopApp.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodDurationLoggable {

    String nameDescription() default "JPA";

    String className() default "";

    int timeThreshold() default 1000;

    boolean showInHelp() default true;
}
