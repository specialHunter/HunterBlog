package com.hunter.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台用户Vo
 *
 * @author Hunter
 * @since 2025/2/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackgroundUserVo {
    /**
     * 权限信息
     */
    private List<String> permissions;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户信息Vo
     */
    private UserInfoVo userInfoVo;


}
