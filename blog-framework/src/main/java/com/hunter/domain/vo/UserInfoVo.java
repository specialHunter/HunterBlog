package com.hunter.domain.vo;

import lombok.Data;

/**
 * 用户信息VO
 *
 * @author Hunter
 * @since 2025/1/23
 */
@Data
public class UserInfoVo {
    private Long id;

    private String nickName;

    private String sex;

    private String avatar;

    private String email;
}
