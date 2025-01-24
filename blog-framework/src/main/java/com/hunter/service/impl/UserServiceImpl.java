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
        // SecurityContextHolder获取当前登录用户信息。
        // todo: 当前，SpringSecurity的上下文中存储登录用户信息，但是取出的时候，都只是为了取id，改成只存id？
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 根据用户id查询用户信息，由于会有更新用户信息的操作，SpringSecurity的上下文中的登录用户信息可能不是最新的，需要从数据库查询
        // todo: 从redis读取缓存，这又涉及到 更新用户信息的操作，写缓存和写数据库的一致性问题
        User user = getById(loginUser.getUser().getId());
        // 将用户信息封装到LoginUserVo中
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.success(userInfoVo);
    }

    @Override
    public <T> ResponseResult<T> updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.success();
    }
}




