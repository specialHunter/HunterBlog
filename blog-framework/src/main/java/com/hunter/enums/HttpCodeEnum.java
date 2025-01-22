package com.hunter.enums;

/**
 * 网络响应枚举类
 * @author Hunter
 * @since 2024/12/30
 */
public enum HttpCodeEnum {
    SUCCESS(200, "操作成功"), CONTENT_CANNOT_EMPTY(411, "评论内容不能为空");

    int code;
    String msg;

    HttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
