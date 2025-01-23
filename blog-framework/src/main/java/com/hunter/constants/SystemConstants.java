package com.hunter.constants;

/**
 * 公共常量类
 *
 * @author Hunter
 * @since 2025/1/1
 */
public class SystemConstants {
    /**
     * 文章已发布
     */
    public static final int ARTICLE_STATUS_RELEASE = 0;

    /**
     * 文章为草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章已删除
     */
    public static final int ARTICLE_DELETED = 1;

    /**
     * 文章未删除
     */
    public static final int ARTICLE_NOT_DELETE = 0;

    /**
     * 分类状态为正常
     */
    public static final String CATEGORY_NORMAL = "0";

    /**
     * 友链状态为审核通过
     */
    public static final String LINK_APPROVED = "0";

    /**
     * 评论类型为文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 评论类型为友链评论
     */
    public static final String LINK_COMMENT = "1";
}
