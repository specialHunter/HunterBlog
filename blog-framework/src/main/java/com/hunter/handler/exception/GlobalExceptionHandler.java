package com.hunter.handler.exception;

import com.hunter.domain.ResponseResult;
import com.hunter.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常统一处理
 *
 * @author Hunter
 * @since 2025/1/21
 */
@RestControllerAdvice // 拦截所有控制器抛出的异常
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public <T> ResponseResult<T> globalExceptionHandler(GlobalException globalException) {
        log.error("全局异常：{}", globalException.getMsg(), globalException);
        return ResponseResult.error(globalException.getCode(), globalException.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseResult<T> exceptionHandler(Exception exception) {
        log.error("出现异常：{}", exception.getMessage(), exception);
        return ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
