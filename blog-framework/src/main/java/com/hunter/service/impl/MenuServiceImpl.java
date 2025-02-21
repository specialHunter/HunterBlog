package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.Menu;
import com.hunter.domain.vo.MenuVo;
import com.hunter.domain.vo.RoutersVo;
import com.hunter.mapper.MenuMapper;
import com.hunter.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表 服务实现类
 *
 * @author Hunter
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2025-02-10 22:18:08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Override
    public List<String> getPermsByUserId(Long userId) {
        // 如果用户id是1，是管理员，返回所有菜单权限
        if (Objects.equals(userId, 1L)) {
            LambdaQueryWrapper<Menu> queryWrapper = Wrappers.<Menu>lambdaQuery()
                    // 菜单类型为 菜单、按钮
                    .in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON)
                    // 菜单状态为正常
                    .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> menuList = list(queryWrapper);
            return menuList.stream().map(Menu::getPerms).collect(Collectors.toList());
        } else {
            // 返回所具有的权限
            return getBaseMapper().getPermsByUserId(userId);
        }
    }

    @Override
    public ResponseResult<RoutersVo> getMenuTreeByUserId(Long userId) {
        // 菜单数据的要求：菜单类型需要为C、M，状态正常、未被删除
        MenuMapper menuMapper = getBaseMapper();
        List<MenuVo> menuVos = null;

        // 如果是管理员，返回所有符合要求的menu
        if (Objects.equals(userId, 1L)) {
            menuVos = menuMapper.getAllMenus();
        } else {
            // 返回当前用户的所有menu权限
            menuVos = menuMapper.getMenusByUserId(userId);
        }
        // 构建菜单树
        List<MenuVo> menuTree = buildMenuTree(menuVos);
        return ResponseResult.success(new RoutersVo(menuTree));
    }


    /**
     * 构建菜单树
     *
     * @param menuVos 菜单Vo列表
     * @return 菜单树
     */
    private List<MenuVo> buildMenuTree(List<MenuVo> menuVos) {
        // 存储每个父id对应的子菜单列表
        Map<Long, List<MenuVo>> childrenMap = new HashMap<>();
        for (MenuVo menuVo : menuVos) {
            childrenMap.computeIfAbsent(menuVo.getParentId(), k -> new ArrayList<>())
                    .add(menuVo);
        }
        return getChildren(0L, childrenMap);
    }

    /**
     * 获取菜单Vo的子菜单集合
     *
     * @param currentMenuId 当前菜单Id
     * @param childrenMap 菜单Vo的子菜单集合
     * @return 子菜单列表
     */
    private List<MenuVo> getChildren(Long currentMenuId, Map<Long, List<MenuVo>> childrenMap) {
        // List.of() 是Java 9 引入的空列表。
        // 如果currentMenuId没有对应的子菜单，后续的map操作不会执行，collect操作会返回空列表。
        return childrenMap.getOrDefault(currentMenuId, List.of()).stream()
                // 递归获取子菜单
                .map(menu -> menu.setChildren(getChildren(menu.getId(), childrenMap)))
                .collect(Collectors.toList());
    }
}




