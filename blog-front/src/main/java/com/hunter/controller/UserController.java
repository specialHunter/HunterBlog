package com.hunter.controller;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.User;
import com.hunter.domain.vo.UserInfoVo;
import com.hunter.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 更新当前用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     * @param <T> 返回类型
     */
    @PutMapping("/userInfo")
    public <T> ResponseResult<T> updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
}
