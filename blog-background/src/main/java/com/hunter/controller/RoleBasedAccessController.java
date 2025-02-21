package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.LoginUser;
import com.hunter.domain.vo.BackgroundUserVo;
import com.hunter.domain.vo.RoutersVo;
import com.hunter.domain.vo.UserInfoVo;
import com.hunter.service.MenuService;
import com.hunter.service.RoleService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 基于角色的访问控制
 *
 * @author Hunter
 * @since 2025/2/10
 */
@RestController
public class RoleBasedAccessController {
    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    /**
     * 获取当前登录用户的权限和角色信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public ResponseResult<BackgroundUserVo> getInfo() {
        // 获取当前登录的用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 根据用户id查询权限信息
        List<String> perms = menuService.getPermsByUserId(loginUser.getUser().getId());

        // 根据用户id查询角色信息
        List<String> roles = roleService.getRolesByUserId(loginUser.getUser().getId());

        // 获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        // 封装数据
        BackgroundUserVo backgroundUserVo = new BackgroundUserVo(perms, roles, userInfoVo);
        return ResponseResult.success(backgroundUserVo);
    }

    /**
     * 获取当前用户能访问的菜单数据
     *
     * @return 菜单树
     */
    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuService.getMenuTreeByUserId(loginUser.getUser().getId());
    }
}
