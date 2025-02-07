package com.hunter.config;

import org.springframework.stereotype.Component;

/**
 * 更新浏览量时的配置类。
 * 由于文章的浏览量在文章表中，在MyMetaObjectHandler的自动填充机制下，
 * 更新浏览量的操作会变更updateBy和updateTime字段，而这两个字段应该单纯地作为文章本身的更新数据，
 * 不能被更新浏览量的定时任务干扰
 *
 * @author Hunter
 * @since 2025/2/7
 */
@Component
public class UpdateViewCountConfig {
    private boolean isUpdateViewCount = false;


    public boolean isUpdateViewCount() {
        return isUpdateViewCount;
    }

    public void setUpdateViewCount(boolean updateViewCount) {
        isUpdateViewCount = updateViewCount;
    }
}
