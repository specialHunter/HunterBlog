package com.hunter.domain.vo;

import lombok.Data;

/**
 * @author Hunter
 * @since 2025/1/1
 */
@Data
public class HotArticleVo {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Long viewCount;
}
