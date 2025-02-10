package com.hunter.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录用户的VO
 *
 * @author Hunter
 * @since 2025/1/19
 */
@Data
@AllArgsConstructor
// 序列化时，null值不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUserVo {
    private String token;

    private UserInfoVo userInfo;
}
