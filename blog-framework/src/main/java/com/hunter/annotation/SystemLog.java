package com.hunter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 *
 * @author Hunter
 * @since 2025/1/24
 */
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时有效
@Target({ElementType.METHOD}) // 注解可以用在方法上
public @interface SystemLog {
    String businessName();
}
