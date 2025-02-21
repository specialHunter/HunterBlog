package com.hunter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Menu;
import com.hunter.domain.vo.RoutersVo;

import java.util.List;

/**
 * 菜单权限表 服务接口
 *
 * @author Hunter
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
 * @createDate 2025-02-10 22:18:08
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户ID查询权限标识集合
     *
     * @param userId 用户ID
     * @return 权限标识集合
     */
    List<String> getPermsByUserId(Long userId);

    /**
     * 根据用户Id查询能访问的菜单数据，返回菜单树
     *
     * @param userId 用户Id
     * @return 菜单数据
     */
    ResponseResult<RoutersVo> getMenuTreeByUserId(Long userId);
}
