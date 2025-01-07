package com.hunter.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hunter
 * @since 2025/1/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleDetailVo extends ArticleVo {

    /**
     * 文章内容
     */
    private String content;
}
