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
     * 用户信息Vo，属性命名与前端保持一致，否则前端后续跳转有问题
     */
    private UserInfoVo user;


}
