package com.hunter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.constants.SystemConstants;
import com.hunter.domain.entity.Menu;
import com.hunter.service.MenuService;
import com.hunter.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<String> getPermsByUserId(Long id) {
        // 如果用户id是1，是管理员，返回所有菜单权限
        if (id == 1L) {
            LambdaQueryWrapper<Menu> queryWrapper = Wrappers.<Menu>lambdaQuery()
                    // 菜单类型为 菜单、按钮
                    .in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON)
                    // 菜单状态为正常
                    .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> menuList = list(queryWrapper);
            return menuList.stream().map(Menu::getPerms).collect(Collectors.toList());
        } else {
            // 返回所具有的权限
            return getBaseMapper().getPermsByUserId(id);
        }
    }
}




