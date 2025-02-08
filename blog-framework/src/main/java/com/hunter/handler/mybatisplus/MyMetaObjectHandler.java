package com.hunter.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hunter.config.UpdateViewCountConfig;
import com.hunter.domain.entity.LoginUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
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
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Resource
    private UpdateViewCountConfig updateViewCountConfig;

    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入数据时，自动填充 create_by、create_time、update_by、update_time 字段
        Long userId = getUserId();
        LocalDateTime dateTime = LocalDateTime.now();
        // 注意设定的fieldType要和filedName实际的字段类型一致，否则会对应不上，插入失败
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, dateTime);
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, dateTime);
    }

    /**
     * 从spring security的上下文中 获取当前登录用户的ID
     *
     * @return 当前登录用户的ID
     */
    private static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 注册用户的场景，SecurityConfig中已设定auth.requestMatchers("/login").anonymous()
        // authentication.getPrincipal()为anonymousUser
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            log.warn("authentication is Not expected: {}", authentication);
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getId();
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 如果不是更新文章浏览量，就自动填充 update_by、update_time 字段
        if (!updateViewCountConfig.isUpdateViewCount()) {
            Long userId = getUserId();
            LocalDateTime dateTime = LocalDateTime.now();
            this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, dateTime);
        }
    }
}
