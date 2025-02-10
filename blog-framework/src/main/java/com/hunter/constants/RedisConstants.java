package com.hunter.constants;

/**
 * redis中存储的key的常量
 *
 * @author Hunter
 * @since 2025/2/7
 */
public class RedisConstants {

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEW_COUNT = "article:viewCount";

    /**
     * 前台登录用户的id
     */
    public static final String FRONT_LOGIN_USER_ID = "front:login:user:id:";

    /**
     * 后台登录用户的id
     */
    public static final String ADMIN_LOGIN_USER_ID = "admin:login:user:id:";
}
