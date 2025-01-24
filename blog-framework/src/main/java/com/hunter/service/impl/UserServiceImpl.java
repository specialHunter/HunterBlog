package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.LoginUser;
import com.hunter.domain.entity.User;
import com.hunter.domain.vo.UserInfoVo;
import com.hunter.enums.HttpCodeEnum;
import com.hunter.exception.GlobalException;
import com.hunter.mapper.UserMapper;
import com.hunter.service.UserService;
import com.hunter.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Hunter
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2025-01-21 22:36:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /**
     * 注入在SecurityConfig中注册的密码编码器
     */
    @Resource
    private PasswordEncoder passwordEncoder;

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

    @Override
    public <T> ResponseResult<T> register(User user) {
        // 用户名，昵称，邮箱不能和数据库中原有的 数据重复。如果某项重复了注册失败
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        // 用户名重复
        if (isExist(queryWrapper, User::getUsername, user.getUsername())) {
            throw new GlobalException(HttpCodeEnum.USERNAME_EXISTS);
        }
        // 昵称重复
        if (isExist(queryWrapper, User::getNickName, user.getNickName())) {
            throw new GlobalException(HttpCodeEnum.NICK_NAME_EXISTS);
        }
        // 邮箱重复
        if (isExist(queryWrapper, User::getEmail, user.getEmail())) {
            throw new GlobalException(HttpCodeEnum.EMAIL_EXISTS);
        }
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 添加到数据库
        save(user);
        return ResponseResult.success();
    }

    /**
     * 判断某个字段是否重复
     *
     * @param queryWrapper 查询条件
     * @param function     字段
     * @param fieldValue   字段值
     * @return true：重复，false：不重复
     */
    private boolean isExist(LambdaQueryWrapper<User> queryWrapper, SFunction<User, String> function,
                            String fieldValue) {
        queryWrapper.clear();
        queryWrapper.eq(function, fieldValue);
        return count(queryWrapper) > 0;
    }
}




