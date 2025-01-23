package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.vo.UserInfoVo;
import com.hunter.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author Hunter
 * @since 2025/1/23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 查询当前用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping("/userInfo")
    public ResponseResult<UserInfoVo> getUserInfo() {
        return userService.getUserInfo();
    }
}
