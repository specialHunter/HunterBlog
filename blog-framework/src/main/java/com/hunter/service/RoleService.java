package com.hunter.service;

import com.hunter.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Hunter
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2025-02-10 22:24:15
*/
public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID查询角色信息集合
     *
     * @param userId userId
     * @return 角色信息列表
     */
    List<String> getRolesByUserId(Long userId);
}
