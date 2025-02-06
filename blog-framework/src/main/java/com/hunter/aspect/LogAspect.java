package com.hunter.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunter.annotation.SystemLog;
import com.hunter.utils.ObjectMapperFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志切面
 *
 * @author Hunter
 * @since 2025/2/5
 */
@Component
@Aspect // 声明一个切面
@Slf4j
public class LogAspect {
    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

    // 将注解SystemLog作为切点，由于SystemLog注解声明用在方法上，所以使用了该注解的方法都会调用该方法
    @Pointcut("@annotation(com.hunter.annotation.SystemLog)")
    public void pt() {
        // 空方法，用于声明切点
    }


    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);
            // 执行被增强的方法
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            // 换行符使用System.lineSeparator()获取，避免不同操作系统的换行符不一致
            log.info("========== End =========={}", System.lineSeparator());
        }
        return ret;
    }

    private void handleAfter(Object ret) throws JsonProcessingException {
        // 打印返回值
        log.info("Response              : {}", objectMapper.writeValueAsString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("========== Start ==========");
        // 发起请求的ip
        log.info("IP                    : {}", request.getRemoteHost());
        // 请求的url
        log.info("Request URL           : {}", request.getRequestURL());
        // 请求的http方法
        log.info("request method        : {}", request.getMethod());
        // 打印 使用系统日志注解的方法 的描述信息
        log.info("BusinessName          : {}", systemLog.businessName());
        // 打印调用controller的全局路径以及执行方法
        log.info("Class Method          : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        // 打印请求参数
        log.info("Request Args          : {}", objectMapper.writeValueAsString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}

