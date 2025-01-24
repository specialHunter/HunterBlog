package com.hunter.service;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.domain.vo.UserInfoVo;

/**
* @author Hunter
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2025-01-21 22:36:43
*/
public interface UserService extends IService<User> {

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    ResponseResult<UserInfoVo> getUserInfo();

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     * @param <T> 返回类型
     */
    <T> ResponseResult<T> updateUserInfo(User user);
}
