package com.hunter.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hunter
 * @since 2025/1/5
 */
@Data
public class ArticleVo {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类名
     */
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 是否置顶（0否，1是）
     */
    private String isTop;

    /**
     * 状态（0已发布，1草稿）
     */
    private String status;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     * 创建时间
     */
    private Date createTime;

}
