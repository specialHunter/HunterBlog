package com.hunter.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Hunter
 * @since 2025/1/19
 */
@Data
@AllArgsConstructor
public class LoginUserVo {
    private String token;

    private UserInfoVo userInfo;

    @Data
    public static class UserInfoVo {
        private int id;

        private String nickName;

        private String sex;

        private String avatar;

        private String email;
    }
}
