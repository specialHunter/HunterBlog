package com.hunter.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunter.enums.HttpCodeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一的响应结果
 *
 * @author Hunter
 * @since 2024/12/30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 序列化时，null值不参与序列化
public class ResponseResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -4627979247168877437L;
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    private ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(HttpCodeEnum.SUCCESS.getCode(), HttpCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseResult<T> failed(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> error(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("序列化ResponseResult出错", exception);
        }
    }
}
