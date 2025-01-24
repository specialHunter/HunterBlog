package com.hunter.enums;

import lombok.Getter;

/**
 * 网络响应枚举类
 * @author Hunter
 * @since 2024/12/30
 */
@Getter
public enum HttpCodeEnum {
    SUCCESS(200, "操作成功"),
    CONTENT_CANNOT_EMPTY(411, "评论内容不能为空"),
    FILE_TYPE_ERROR(415, "不支持的文件类型，请上传jpg、png、jpeg格式的文件"),
    USERNAME_EXISTS(416, "用户名已存在"),
    NICK_NAME_EXISTS(417, "昵称已存在"),
    EMAIL_EXISTS(418, "邮箱已存在");

    final int code;
    final String msg;

    HttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
