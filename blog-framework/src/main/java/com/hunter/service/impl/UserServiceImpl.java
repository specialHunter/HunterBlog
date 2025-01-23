package com.hunter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.LoginUser;
import com.hunter.domain.entity.User;
import com.hunter.domain.vo.UserInfoVo;
import com.hunter.mapper.UserMapper;
import com.hunter.service.UserService;
import com.hunter.utils.BeanCopyUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Hunter
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2025-01-21 22:36:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public ResponseResult<UserInfoVo> getUserInfo() {
        // SecurityContextHolder获取当前登录用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 将用户信息封装到LoginUserVo中
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.success(userInfoVo);
    }
}




