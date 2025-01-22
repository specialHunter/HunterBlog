package com.hunter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.domain.entity.User;
import com.hunter.service.UserService;
import com.hunter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Hunter
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2025-01-21 22:36:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




