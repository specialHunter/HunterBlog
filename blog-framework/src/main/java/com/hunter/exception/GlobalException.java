package com.hunter.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 全局异常类
 *
 * @author Hunter
 * @since 2025/1/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2519478486800846335L;

    private int code;

    private String msg;
}
