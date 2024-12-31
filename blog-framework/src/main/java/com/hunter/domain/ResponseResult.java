package com.hunter.domain;

import com.hunter.enums.HttpCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一的响应结果
 *
 * @author Hunter
 * @since 2024/12/30
 */
@Data
public class ResponseResult<T> implements Serializable {

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

    public ResponseResult() {
        this.code = HttpCodeEnum.SUCCESS.getCode();
        this.msg = HttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResult<Object> okResult(Object data) {
        return new ResponseResult<>(HttpCodeEnum.SUCCESS.getCode(), HttpCodeEnum.SUCCESS.getMsg(), data);
    }


}
