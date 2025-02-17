package com.hunter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.entity.Role;
import com.hunter.mapper.RoleMapper;
import com.hunter.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Hunter
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2025-02-10 22:24:15
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public List<String> getRolesByUserId(Long userId) {
        // 如果是管理员，返回的角色集合中，只包含管理员角色（以业务逻辑规定为准）
        if (userId == 1L) {
            List<String> roles = new ArrayList<>();
            roles.add(SystemConstants.ROLE_ADMIN);
            return roles;
        } else {
            // 否则包含所有角色信息
            return getBaseMapper().getRolesByUserId(userId);
        }
    }
}




