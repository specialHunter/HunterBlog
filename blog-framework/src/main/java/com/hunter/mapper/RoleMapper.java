package com.hunter.mapper;

import com.hunter.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Hunter
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2025-02-10 22:24:15
* @Entity com.hunter.domain.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色信息集合
     * @param userId userId
     * @return 角色信息集合
     */
    List<String> getRolesByUserId(Long userId);
}




