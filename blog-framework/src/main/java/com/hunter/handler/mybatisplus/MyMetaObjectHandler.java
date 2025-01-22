package com.hunter.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hunter.domain.entity.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatisPlus 元对象处理器，用于 自动填充 需要写入数据库的对象的字段
 * 数据库中每张表都有 create_by、create_time、update_by、update_time 这四个字段
 *
 * @author Hunter
 * @since 2025/1/22
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入数据时，自动填充 create_by、create_time、update_by、update_time 字段
        Long userId = getUserId();
        LocalDateTime dateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, dateTime);
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, dateTime);
    }

    /**
     * 从spring security的上下文中 获取当前登录用户的ID
     * @return 当前登录用户的ID
     */
    private static Long getUserId() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser().getId();
    }

    @Override
    public void updateFill(MetaObject metaObject) {
         // 更新数据时，自动填充 update_by、update_time 字段
        Long userId = getUserId();
        LocalDateTime dateTime = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, dateTime);
    }
}
