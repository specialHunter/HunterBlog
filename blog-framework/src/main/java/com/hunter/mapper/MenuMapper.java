package com.hunter.mapper;

import com.hunter.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hunter.domain.vo.MenuVo;

import java.util.List;

/**
 * @author Hunter
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
 * @createDate 2025-02-10 22:18:08
 * @Entity com.hunter.domain.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID查询权限标识集合
     *
     * @param userId userId
     * @return 权限标识集合
     */
    List<String> getPermsByUserId(Long userId);

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    List<MenuVo> getAllMenus();

    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId userId
     * @return 菜单列表
     */
    List<MenuVo> getMenusByUserId(Long userId);
}




